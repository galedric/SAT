package sat.radio.message;

import sat.crypto.RSAKey;

public class MessageSendRSAKey extends Message {
	private RSAKey publicKey;
	
	public String toString() {
		return "I'm a SendRSAKey";
	}
}
