package sat.plane;

import java.io.IOException;

import sat.radio.RadioID;
import sat.radio.client.RadioClient;
import sat.radio.client.RadioClientDelegate;
import sat.radio.engine.client.RadioClientEngine;
import sat.radio.message.Message;
import sat.utils.cli.Config;
import sat.utils.geo.Coordinates;

public class Plane {
	/**
	 * La configuration par défaut d'un avion. Sert de modèle à la contruction
	 * de la configuration spécifique aux instances d'avions.
	 */
	private static Config defaults;

	/**
	 * La configuration spécifique à une instance d'un avion.
	 */
	private Config config;

	private RadioClient radio;

	public Plane() {
		if(defaults == null)
			initDefaults();

		config = new Config(defaults);
	}

	/**
	 * Initialise la configuration par défaut avec les valeurs appropriées.
	 */
	private static void initDefaults() {
		defaults = new Config();

		defaults.setProperty("radio.verbose", "no");
		defaults.setProperty("radio.ciphered", "yes");
		defaults.setProperty("radio.legacy", "no");
		defaults.setProperty("radio.keylength", "1024");
	}

	/**
	 * Retourne l'objet de configuration de l'avion.
	 */
	public Config getConfig() {
		return config;
	}

	public Coordinates getLocation() {
		return new Coordinates(0, 0, 0);
	}

	public void connect(RadioClientEngine engine) throws IOException {
		if(radio == null)
			radio = new RadioClient(new Delegate(), config.getInt("radio.keylength"), "PLN");
		radio.connect(engine);
	}

	// - - - Delegate - - -

	private class Delegate implements RadioClientDelegate {
		public Coordinates getLocation() {
			return Plane.this.getLocation();
		}

		public void onMessage(RadioID tower, Message message) {
			System.out.println("Plane got message " + message + " from " + message.getID());
		}
	}
}
