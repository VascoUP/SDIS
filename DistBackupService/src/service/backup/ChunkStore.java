package service.backup;

import service.Message;

public class ChunkStore extends Message {

	private int chunkId; 

	public ChunkStore(String version, int senderId, int fileId, int chunkId) {
		super(BackUpConstants.ANSWER_MESSAGE_TYPE, version, senderId, fileId);
		
		this.chunkId = chunkId;
	}
	
	@Override
	public String toString() {
		String superString = "" + super.toString();
		return superString + " " + chunkId;
	}
}
