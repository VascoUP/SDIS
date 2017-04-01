package message.general;

public class BasicMessage {
	private String[] head;
	private byte[] body;
	
	public BasicMessage(String[] head, byte[] body) {
		this.head = head;
		this.body = body;
	}
	
	public String[] getHead() {
		return head;
	}
	
	public byte[] getBody() {
		return body;
	}
}
