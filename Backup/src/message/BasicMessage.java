package message;

public class BasicMessage {
	private final String[] head;
	private final byte[] body;
	
	public BasicMessage(String[] head, byte[] body) {
		this.head = head;
		this.body = body;
	}

    public String[] getHead() {
		return head;
	}
}
