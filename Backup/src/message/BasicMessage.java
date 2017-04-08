package message;

/**
 * 
 * This class builds the basic message
 *
 */
public class BasicMessage {
	private String[] head;	//Message's header
	private byte[] body;	//Message's body
	
	/**
	 * BasicMessage's constructor
	 * @param head Message's header
	 * @param body Message's body
	 */
	public BasicMessage(String[] head, byte[] body) {
		this.head = head;
		this.body = body;
	}
	
	/**
	 * Gets the message's body
	 * @return The message's body
	 */
	public byte[] getBody() {
		return body;
	}
	
	/**
	 * Gets the message's header
	 * @return The message's header
	 */
	public String[] getHead() {
		return head;
	}
	
	/**
	 * Gets a string with the message
	 * @return The string with the message
	 */
	@Override
	public String toString() {
		return MessageToString.getName(this);
	} 
	
	/**
	 * Gets the message's hash code
	 * @return The message's hash code
	 */
	@Override
   public int hashCode() {
		int hash = 0;
		for( String str : head )
			hash += str.hashCode();
		return hash;
   }
	
	/**
	 * Verifies if a message is equal to another one
	 * @param o Object that will be compared
	 * @return true if the messages are equal, false otherwise
	 */
	@Override
	public boolean equals(Object o) {		
		if( o.getClass() != BasicMessage.class )
			return false;
		BasicMessage message = (BasicMessage)o;
		String[] messageHead = message.getHead();
		if( head.length != messageHead.length ) //Compares the headers length
			return false;
		for( int i = 0; i < message.getHead().length; i++ )
			if( !head[i].equals(messageHead[i]) )
				return false;
		return true;
	}
}
