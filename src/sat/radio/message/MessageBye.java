package sat.radio.message;

import sat.radio.RadioID;

public class MessageBye extends Message {
	protected MessageType type = MessageType.BYE;
	protected int priority = 4;

	public MessageBye(RadioID id, int px, int py) {
		super(id, px, py);
	}

	public String toString() {
		return "I'm a Bye!";
	}

	private static final long serialVersionUID = 421645651039774637L;
}
