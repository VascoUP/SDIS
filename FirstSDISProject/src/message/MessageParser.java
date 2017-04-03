package message;

public class MessageParser {
	public static boolean parseFlag(byte[] message, int index) {
		for( int i = 0; i < MessageConst.MESSAGE_FLAG.length && index < message.length; i++, index++ ) {
			if( MessageConst.MESSAGE_FLAG[i] != message[index] ) 
				return false;
		}
		return index <= message.length;
	}

	public static byte[] parseHead(byte[] message, int index) {
		byte[] messageHead = null;
		int messageIndex = index;
		
		for( ; messageIndex < message.length; messageIndex++ ) {
			if( parseFlag(message, messageIndex) ) {
				System.out.println("parseHead flag index: " + messageIndex);
				//end of the head
				messageHead = new byte[messageIndex - index];
				System.arraycopy(message, index, messageHead, 0, messageIndex - index);
				break;
			}
		}
		
		return messageHead;
	}
	
	public static byte[] parseBody(byte[] message, int index) {
		byte[] tmpBody = new byte[message.length - (index + 2)];
		System.arraycopy(message, index + 2, tmpBody, 0, message.length - (2 + index));
		return MessageOperation.trim(tmpBody);
	}
	
	public static String[] processMessageHead(byte[] messageHead) {
		messageHead = MessageOperation.trim(messageHead);
		return MessageOperation.splitMultipleSpaces(new String(messageHead));
		
	}

	public static BasicMessage parseMessage(byte[] message) {
		int messageIndex = 0;
		String[] head;
		byte[] body, messageHead;
		
		if( !parseFlag(message, messageIndex) ) {
			System.out.println("First parse Flage error");
			return null;
		}
		messageIndex += 2;
		
		if( (messageHead = parseHead(message, messageIndex)) == null ) {
			System.out.println("Parse head error");
			return null;
		}
		messageIndex += messageHead.length;

		head = processMessageHead(messageHead);
		
		if( !parseFlag(message, messageIndex) ) {
			System.out.println("Second parse flag error");
			return null;
		}
		messageIndex += 2;
		
		if( !parseFlag(message, messageIndex) ) {
			System.out.println("Third parse flag error");
			return null;
		}
		messageIndex += 2;		
		
		if( (body = parseBody(message, messageIndex)) == null ) {
			System.out.println("Parse body error");
			return null;
		}
		
		BasicMessage bm = new BasicMessage(head, body);
		
		return bm;
	}	
}
