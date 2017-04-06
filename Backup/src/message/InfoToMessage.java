package message;

public class InfoToMessage {
	public static BasicMessage toMessage(MessageInfo info) {
		String type = info.getMessageType();
		switch( type ) {
		case MessageConst.PUTCHUNK_MESSAGE_TYPE:
			return putChunkToMessage((MessageInfoPutChunk)info);
		case MessageConst.STORED_MESSAGE_TYPE:
			return storedToMessage((MessageInfoStored)info);
		case MessageConst.RESTORE_MESSAGE_TYPE:
			return getChunkToMessage((MessageInfoGetChunk)info);
		case MessageConst.CHUNK_MESSAGE_TYPE:
			return chunkToMessage((MessageInfoChunk)info);
		}
		return null;
	}
	
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
	
	private static BasicMessage storedToMessage(MessageInfoStored info) {
		String[] head = new String[5];
		head[0] = info.getMessageType();
		head[1] = info.getVersion();
		head[2] = "" + info.getSenderID();
		head[3] = info.getFileID();
		head[4] = "" + info.getChunkID();
		return new BasicMessage(head, new byte[0]);
	}
	
	private static BasicMessage getChunkToMessage(MessageInfoGetChunk info) {
		String[] head = new String[5];
		head[0] = info.getMessageType();
		head[1] = info.getVersion();
		head[2] = "" + info.getSenderID();
		head[3] = info.getFileID();
		head[4] = "" + info.getChunkID();
		return new BasicMessage(head, new byte[0]);
	}
	
	private static BasicMessage chunkToMessage(MessageInfoChunk info) {
		String[] head = new String[5];
		head[0] = info.getMessageType();
		head[1] = info.getVersion();
		head[2] = "" + info.getSenderID();
		head[3] = info.getFileID();
		head[4] = "" + info.getChunkID();
		return new BasicMessage(head, info.getChunk());
	}
}
