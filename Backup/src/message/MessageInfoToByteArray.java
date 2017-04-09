package message;

/**
 * 
 * This class converts a message into a byte's array
 *
 */
public class MessageInfoToByteArray {
	/**
	 * Gets the message's header converted into a byte's array
	 * @param info Array of byte's array with the message
	 * @return A Byte's array with the message's header
	 */
	private static byte[] infoHeadToByteArray(byte[][] info) {
		int headLength = 0;
		int messageIndex = 0;
		byte[] space = " ".getBytes();
		
		for( int i = 0; i < info.length - 1; i++ )
			headLength += info[i].length;
		
		byte[] head = new byte[headLength + info.length - 2];
		
		for( int i = 0; i < info.length - 1; i++ ) {
			System.arraycopy(info[i], 0, head, messageIndex, info[i].length);
			messageIndex += info[i].length;
			
			if( i < info.length - 2 ) {
				System.arraycopy(space, 0, head, messageIndex, space.length);
				messageIndex += space.length;
			}
		}
		
		return head;
	}
	
	/**
	 * Gets the message's converted into a byte's array
	 * @param info Array of byte's array with the message
	 * @return A Byte's array with the message's
	 */
	public static byte[] infoToByteArray(MessageInfo info) {
		byte[][] allInfos = info.getAll();
		byte[] head = infoHeadToByteArray(allInfos);	//Header
		byte[] body = allInfos[allInfos.length - 1];	//body
		byte[] byteArray = new byte[head.length + body.length + 6]; //Header + Body + Flags
		
		System.arraycopy(MessageConst.MESSAGE_FLAG, 0, 
				byteArray, 0, 2);
		System.arraycopy(head, 0, 
				byteArray, 2, head.length);
		System.arraycopy(MessageConst.MESSAGE_FLAG, 0, 
				byteArray, 2 + head.length, 2);
		System.arraycopy(MessageConst.MESSAGE_FLAG, 0, 
				byteArray, 4 + head.length, 2);
		System.arraycopy(body, 0, 
				byteArray, 6 + head.length, body.length);
		
		return byteArray;
	}
}
