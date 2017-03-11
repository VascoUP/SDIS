package service.backup;

import service.Message;

public class ChunkBackUp extends Message {

	private int chunkId; 

	public ChunkBackUp(String version, int senderId, int fileId, int chunkId) {
		super(BackUpConstants.REQUEST_MESSAGE_TYPE, version, senderId, fileId);
		
		this.chunkId = chunkId;
	}
	
	@Override
	public String toString() {
		String superString = "" + super.toString();
		return superString + " " + chunkId;
	}
}
