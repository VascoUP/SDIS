package message;

public class BasicMessage {
	private String[] head;
	private byte[] body;
	
	public BasicMessage(String[] head, byte[] body) {
		this.head = head;
		this.body = body;
	}
	
	public byte[] getBody() {
		return body;
	}
	
	public String[] getHead() {
		return head;
	}
}
