package sat.radio.message.stream;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import sat.radio.message.Message;
import sat.radio.message.MessageHello;
import sat.radio.message.MessageType;

/**
 * Flux d'entrée de message compatible avec le protocole de sérialisation de
 * l'ITP.
 */
public class LegacyMessageInputStream extends MessageInputStream {
	/**
	 * Un flux d'entrée de données utilisé pour lire les différents composants
	 * du message sérialisé.
	 */
	private DataInputStream dis;

	/**
	 * Crée un nouveau flux d'entrée de message ITP-compliant.
	 * 
	 * @param in
	 *            Le flux d'entrée depuis lequel lire les données serialisées.
	 */
	public LegacyMessageInputStream(InputStream in) {
		super(in);

		// Le message est sérialisé en tant que séquence de primitives Java 
		// qui pourront être lues avec un DataInputStream.
		dis = new DataInputStream(in);
	}

	/**
	 * Lis un message depuis le flux d'entrée
	 */
	public Message readMessage() throws IOException {
		// Lecture des paramètres communs à tous les messages.
		// Attention, l'ordre de lecture est important ! (obviously)
		byte[] planeID = fill(new byte[8]);

		int length = dis.readInt();
		int priority = dis.readInt();

		int posx = dis.readInt();
		int posy = dis.readInt();

		// Le type du message
		MessageType type = MessageType.values()[dis.readInt()];

		// Message qui sera retourné.
		Message message = null;

		switch(type) {
			case HELLO:
				byte reserved = dis.readByte();
				//message = new MessageHello(reserved);
				break;

			case DATA:
				break;

			case MAYDAY:
				break;

			case SENDRSA:
				int keySize = dis.readInt();

				int modulusLength = dis.readInt();
				byte[] modulus = fill(new byte[modulusLength]);

				int publicKeyLength = dis.readInt();
				byte[] publicKey = fill(new byte[publicKeyLength]);

				break;

			case CHOKE:
				break;

			case UNCHOKE:
				break;

			case BYE:
				break;

			case ROUTING:
				break;

			case KEEPALIVE:
				break;

			case LANDINGREQUEST:
				break;
		}

		return message;
	}

	/**
	 * Replis un buffer donné. En d'autre termes: lis autant de bytes que la
	 * taille du buffer passé en paramètre depuis le flux d'entrée interne et
	 * les écrits dans ce même buffer.
	 * 
	 * @param buffer
	 *            Un buffer de byte qui sera rempli avec les bytes disponible
	 *            dans le flux d'entrée interne.
	 * 
	 * @return Le buffer passé en paramètre. Cette valeur de retour est le même
	 *         buffer que celui passé en paramètre. Elle est disponible afin de
	 *         simplifier la syntaxe d'utilisation de cette méthode.
	 * 
	 * @throws IOException
	 *             Cette méthode lit des bytes depuis le flux interne. Cette
	 *             lecture est succeptible de lever une exception.
	 */
	private byte[] fill(byte[] buffer) throws IOException {
		for(int i = 0; i < buffer.length; i++) {
			buffer[i] = dis.readByte();
		}

		return buffer;
	}
}
