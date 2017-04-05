package message;

public class MessageInfoChunk extends MessageInfo {
	private final int chunkID;

	MessageInfoChunk(String messageType, String version, int senderID, String fileID, int chunkID) {
		super( messageType, version, senderID, fileID );
		this.chunkID = chunkID;
	}

	@Override
	public boolean equals(Object o) {
		MessageInfoChunk info = (MessageInfoChunk)o;
		return 	super.equals(o) && 
				info.getChunkID() == chunkID;
	}

    private int getChunkID() {
		return chunkID;
	}

}
