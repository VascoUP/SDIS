package message;

/**
 * 
 * This class builds the message's information
 *
 */
public class MessageToInfo {
	/**
	 * Gets the MessageInfo from the message's header
	 * @param message Basic message that will be analyzed
	 * @return The MessageInfo that corresponds to the message's header
	 */
	public static MessageInfo messageToInfo(BasicMessage message) {
		if( message.getHead().length < 1 )
			return null;
		
		switch( message.getHead()[0] ) {
		case MessageConst.PUTCHUNK_MESSAGE_TYPE:
			return messageToPutChunk(message);
		case MessageConst.STORED_MESSAGE_TYPE:
			return messageToStored(message);
		case MessageConst.RESTORE_MESSAGE_TYPE:
			return messageToGetChunk(message);
		case MessageConst.CHUNK_MESSAGE_TYPE:
			return messageToChunk(message);
		case MessageConst.DELETE_MESSAGE_TYPE:
			return messageToDelete(message);
		case MessageConst.REMOVED_MESSAGE_TYPE:
			return messageToRemoved(message);
		}
		
		return null;
	}
	
	/**
	 * Gets the MessageInfoPutChunk from the basic message's header
	 * @param message Basic message that will be analyzed
	 * @return The MessageInfoPutChunk converted from the basic message's header
	 */
	private static MessageInfoPutChunk messageToPutChunk(BasicMessage message) {
		String[] head = message.getHead();
		if( head.length != MessageConst.PUTCHUNK_MESSAGE_LENGTH )
			return null;
		return new MessageInfoPutChunk(	head[1], 
										Integer.parseInt(head[2]), head[3], 
										Integer.parseInt(head[4]), Integer.parseInt(head[5]), 
										message.getBody());
	}
	
	/**
	 * Gets the MessageInfoStored from the basic message's header
	 * @param message Basic message that will be analyzed
	 * @return The MessageInfoStored converted from the basic message's header
	 */
	private static MessageInfoStored messageToStored(BasicMessage message) {
		String[] head = message.getHead();
		if( head.length != MessageConst.STORED_MESSAGE_LENGTH )
			return null;
		return new MessageInfoStored(	head[1], 
										Integer.parseInt(head[2]), head[3], 
										Integer.parseInt(head[4]));
	}

	/**
	 * Gets the MessageInfoChunk from the basic message's header
	 * @param message Basic message that will be analyzed
	 * @return The MessageInfoChunk converted from the basic message's header
	 */
	private static MessageInfoChunk messageToChunk(BasicMessage message) {
		String[] head = message.getHead();
		if( head.length != MessageConst.CHUNK_MESSAGE_LENGTH )
			return null;
		return new MessageInfoChunk(	head[1], 
										Integer.parseInt(head[2]), head[3], 
										Integer.parseInt(head[4]), 
										message.getBody());
	}
	
	/**
	 * Gets the MessageInfoGetChunk from the basic message's header
	 * @param message Basic message that will be analyzed
	 * @return The MessageInfoGetChunk converted from the basic message's header
	 */
	private static MessageInfoGetChunk messageToGetChunk(BasicMessage message) {
		String[] head = message.getHead();
		if( head.length != MessageConst.RESTORE_MESSAGE_LENGTH )
			return null;
		return new MessageInfoGetChunk(	head[1], 
										Integer.parseInt(head[2]), head[3], 
										Integer.parseInt(head[4]));
	}

	/**
	 * Gets the MessageInfoDelete from the basic message's header
	 * @param message Basic message that will be analyzed
	 * @return The MessageInfoDelete converted from the basic message's header
	 */
	private static MessageInfoDelete messageToDelete(BasicMessage message) {
		String[] head = message.getHead();
		if( head.length != MessageConst.DELETE_MESSAGE_LENGTH )
			return null;
		return new MessageInfoDelete(	head[1], 
										Integer.parseInt(head[2]), 
										head[3]);
	}

	/**
	 * Gets the MessageInfoRemoved from the basic message's header
	 * @param message Basic message that will be analyzed
	 * @return The MessageInfoRemoved converted from the basic message's header
	 */
	private static MessageInfoRemoved messageToRemoved(BasicMessage message) {
		String[] head = message.getHead();
		if( head.length != MessageConst.STORED_MESSAGE_LENGTH )
			return null;
		return new MessageInfoRemoved(	head[1], 
										Integer.parseInt(head[2]), head[3], 
										Integer.parseInt(head[4]));
	}
}
