package message;

public class MessageToInfo {
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
		}
		
		return null;
	}
	
	
	private static MessageInfoPutChunk messageToPutChunk(BasicMessage message) {
		String[] head = message.getHead();
		if( head.length != MessageConst.PUTCHUNK_MESSAGE_LENGTH )
			return null;
		return new MessageInfoPutChunk(	head[1], 
										Integer.parseInt(head[2]), head[3], 
										Integer.parseInt(head[4]), Integer.parseInt(head[5]), 
										message.getBody());
	}
	
	private static MessageInfoStored messageToStored(BasicMessage message) {
		String[] head = message.getHead();
		if( head.length != MessageConst.STORED_MESSAGE_LENGTH )
			return null;
		return new MessageInfoStored(	head[1], 
										Integer.parseInt(head[2]), head[3], 
										Integer.parseInt(head[4]));
	}

	private static MessageInfoChunk messageToChunk(BasicMessage message) {
		String[] head = message.getHead();
		if( head.length != MessageConst.CHUNK_MESSAGE_LENGTH )
			return null;
		return new MessageInfoChunk(	head[1], 
										Integer.parseInt(head[2]), head[3], 
										Integer.parseInt(head[4]), 
										message.getBody());
	}
	
	private static MessageInfoGetChunk messageToGetChunk(BasicMessage message) {
		String[] head = message.getHead();
		if( head.length != MessageConst.RESTORE_MESSAGE_LENGTH )
			return null;
		return new MessageInfoGetChunk(	head[1], 
										Integer.parseInt(head[2]), head[3], 
										Integer.parseInt(head[4]));
	}

	private static MessageInfoDelete messageToDelete(BasicMessage message) {
		String[] head = message.getHead();
		if( head.length != MessageConst.DELETE_MESSAGE_LENGTH )
			return null;
		return new MessageInfoDelete(	head[1], 
										Integer.parseInt(head[2]), 
										head[3]);
	}
}
