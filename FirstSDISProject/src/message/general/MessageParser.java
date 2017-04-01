package message.general;

public class MessageParser {
	public static boolean parseFlag(byte[] message, int index) {
		for( int i = 0; i < MessageConst.MESSAGE_FLAG.length && index < message.length; i++, index++ ) {
			if( MessageConst.MESSAGE_FLAG[i] != message[index] ) 
				return false;
		}
		return true;
	}

	public static String[] parseHead(byte[] message, int index) {
		String[] head = null;
		int messageIndex = index;
		
		for( ; messageIndex < message.length; messageIndex++ ) {
			if( parseFlag(message, messageIndex) ) {
				//end of the head
				byte[] messageHead = new byte[messageIndex - index];
				head = MessageOperation.splitMultipleSpaces(new String(messageHead));
				System.arraycopy(message, index, head, 0, messageIndex - index);
				break;
			}
		}
		
		return head;
	}
	
	public static byte[] parseBody(byte[] message, int index) {
		byte[] tmpBody = new byte[message.length - (index + 2)];
		System.arraycopy(message, index + 2, tmpBody, 0, message.length - (2 + index));
		return MessageOperation.trim(tmpBody);
	}

	public static BasicMessage parseMessage(byte[] message) {
		int messageIndex = 0;
		String[] head;
		byte[] body;
		
		if( parseFlag(message, messageIndex) ) 
			return null;
		messageIndex += 2;
		
		if( (head = parseHead(message, messageIndex)) == null )
			return null;
		messageIndex += head.length;
		
		if( parseFlag(message, messageIndex) ) 
			return null;
		messageIndex += 2;
		
		if( parseFlag(message, messageIndex) ) 
			return null;
		messageIndex += 2;		
		
		if( (body = parseBody(message, messageIndex)) == null )
			return null;
		
		BasicMessage bm = new BasicMessage(head, body);
		
		return bm;
	}	
}
