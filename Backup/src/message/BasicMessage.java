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
		int hash = 0;
		for( String str : head )
			hash += str.hashCode();
		return hash;
   }
	
	@Override
	public boolean equals(Object o) {		
		if( o.getClass() != BasicMessage.class )
			return false;
		BasicMessage message = (BasicMessage)o;
		String[] messageHead = message.getHead();
		if( head.length != messageHead.length )
			return false;
		for( int i = 0; i < message.getHead().length; i++ )
			if( !head[i].equals(messageHead[i]) )
				return false;
		return true;
	}
}
