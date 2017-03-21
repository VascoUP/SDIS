package message.backup;

import message.Message;

public class StoredChunkMessage extends Message {

	private int chunkId; 

	public StoredChunkMessage(String version, int senderId, int fileId, int chunkId) {
		super(ChunkConst.REQUEST_MESSAGE_TYPE, version, senderId, fileId);
		
		this.chunkId = chunkId;
	}
	
	@Override
	public String toString() {
		String superString = "" + super.toString();
		return superString + " " + chunkId;
	}
}
