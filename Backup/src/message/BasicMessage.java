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
	
	
	@Override
	public String toString() {
		return MessageToString.getName(this);
	} 
	
	@Override
   public int hashCode() {
		return MessageToString.getName(this).hashCode();
   }
	
	@Override
	public boolean equals(Object o) {		
		return o.getClass() == BasicMessage.class &&
				MessageToString.getName((BasicMessage)o).equals(MessageToString.getName(this));
	}
}
