package message;

/**
 * 
 * This class converts the information into a message
 *
 */
public class InfoToMessage {
	/**
	 * Converts a message's information into a basic message
	 * @param info Message's information
	 * @return The basic message after the message's information is converted
	 */
	public static BasicMessage toMessage(MessageInfo info) {
		String type = info.getMessageType();
		switch( type ) {
		case MessageConst.PUTCHUNK_MESSAGE_TYPE:
			return putChunkToMessage((MessageInfoPutChunk)info); 	//Put chunk message type
		case MessageConst.STORED_MESSAGE_TYPE:
			return storedToMessage((MessageInfoStored)info); 		//Stored message type
		case MessageConst.RESTORE_MESSAGE_TYPE:
			return getChunkToMessage((MessageInfoGetChunk)info);	//Restore message type
		case MessageConst.CHUNK_MESSAGE_TYPE:
			return chunkToMessage((MessageInfoChunk)info);			//Chunk message type
		case MessageConst.DELETE_MESSAGE_TYPE:
			return deleteToMessage((MessageInfoDelete)info);		//Delete message type
		case MessageConst.REMOVED_MESSAGE_TYPE:
			return removedToMessage((MessageInfoRemoved)info);		//Removed message type
		}
		return null;
	}
	
	/**
	 * Puts the PutChunk's information into a message
	 * @param info PutChunk's information
	 * @return The basic message created with the PutChunk's information
	 */
	private static BasicMessage putChunkToMessage(MessageInfoPutChunk info) {
		String[] head = new String[6];
		head[0] = info.getMessageType();
		head[1] = info.getVersion();
		head[2] = "" + info.getSenderID();
		head[3] = info.getFileID();
		head[4] = "" + info.getChunkID();
		head[5] = "" + info.getReplicationDegree();
		return new BasicMessage(head, info.getChunk());
	}
	
	/**
	 * Puts the stored's information into a message
	 * @param info Stored's information
	 * @return The basic message created with the stored's information
	 */
	private static BasicMessage storedToMessage(MessageInfoStored info) {
		String[] head = new String[5];
		head[0] = info.getMessageType();
		head[1] = info.getVersion();
		head[2] = "" + info.getSenderID();
		head[3] = info.getFileID();
		head[4] = "" + info.getChunkID();
		return new BasicMessage(head, new byte[0]);
	}
	
	/**
	 * Puts the restore information into a message
	 * @param info Restore information
	 * @return The basic message created with the restore information
	 */
	private static BasicMessage getChunkToMessage(MessageInfoGetChunk info) {
		String[] head = new String[5];
		head[0] = info.getMessageType();
		head[1] = info.getVersion();
		head[2] = "" + info.getSenderID();
		head[3] = info.getFileID();
		head[4] = "" + info.getChunkID();
		return new BasicMessage(head, new byte[0]);
	}
	
	/**
	 * Puts the chunk's information into a message
	 * @param info Chunk's information
	 * @return The basic message created with the chunk's information
	 */
	private static BasicMessage chunkToMessage(MessageInfoChunk info) {
		String[] head = new String[5];
		head[0] = info.getMessageType();
		head[1] = info.getVersion();
		head[2] = "" + info.getSenderID();
		head[3] = info.getFileID();
		head[4] = "" + info.getChunkID();
		return new BasicMessage(head, info.getChunk());
	}
	
	/**
	 * Puts the delete information into a message
	 * @param info Delete information
	 * @return The basic message created with the delete information
	 */
	private static BasicMessage deleteToMessage(MessageInfoDelete info) {
		String[] head = new String[5];
		head[0] = info.getMessageType();
		head[1] = info.getVersion();
		head[2] = "" + info.getSenderID();
		head[3] = info.getFileID();
		return new BasicMessage(head, new byte[0]);
	}
	
	/**
	 * Puts the remove information into a message
	 * @param info Remove information
	 * @return The basic message created with the remove information
	 */
	private static BasicMessage removedToMessage(MessageInfoRemoved info) {
		String[] head = new String[5];
		head[0] = info.getMessageType();
		head[1] = info.getVersion();
		head[2] = "" + info.getSenderID();
		head[3] = info.getFileID();
		head[4] = "" + info.getChunkID();
		return new BasicMessage(head, new byte[0]);
	}
}
