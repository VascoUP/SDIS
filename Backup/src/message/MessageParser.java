package message;

/**
 * 
 * This class builds a message's parser
 *
 */
public class MessageParser {
	/**
	 * Parses the message's flags
	 * @param message Message that will be parsed
	 * @param index Message's index
	 * @return true if the index value is less or equal than the message's length, false otherwise
	 */
	private static boolean parseFlag(byte[] message, int index) {
		for( int i = 0; i < MessageConst.MESSAGE_FLAG.length && index < message.length; i++, index++ ) {
			if( MessageConst.MESSAGE_FLAG[i] != message[index] ) 
				return false;
		}
		return index <= message.length;
	}
	
	/**
	 * Parses the message's header
	 * @param message Message to be parsed
	 * @param index Message's index
	 * @return The header parsed
	 */
	private static byte[] parseHead(byte[] message, int index) {
		byte[] messageHead = null;
		int messageIndex = index;
		
		for( ; messageIndex < message.length; messageIndex++ ) {
			if( parseFlag(message, messageIndex) ) {
				//end of the head
				messageHead = new byte[messageIndex - index];
				System.arraycopy(message, index, messageHead, 0, messageIndex - index);
				break;
			}
		}
		
		return messageHead;
	}
	
	/**
	 * Parses the message's body
	 * @param message Message to be parsed
	 * @param index Message's index
	 * @return The body parsed
	 */
	private static byte[] parseBody(byte[] message, int index) {
		byte[] tmpBody = new byte[message.length - index];
		System.arraycopy(message, index, tmpBody, 0, message.length - index);
		return MessageOperation.trim(tmpBody);
	}
	
	/**
	 * Parses the message creating a basic message
	 * @param message Message that will be parsed
	 * @return The basic message resulting from the message parsed
	 */
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

	/**
	 * Processes the message's header
	 * @param messageHead Message's header
	 * @return The message's header splitted
	 */
	public static String[] processMessageHead(byte[] messageHead) {
		messageHead = MessageOperation.trim(messageHead);
		return MessageOperation.splitMultipleSpaces(new String(messageHead));
		
	}	
}
