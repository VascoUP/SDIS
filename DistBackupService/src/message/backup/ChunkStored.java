package message.backup;

import message.Message;

public class ChunkStored extends Message {

	private int chunkId;
	private String chunk;

	public ChunkStored(String version, int senderId, int fileId, int chunkId, String chunk) {
		super(ChunkConst.ANSWER_MESSAGE_TYPE, version, senderId, fileId);
		
		this.chunkId = chunkId;
		this.chunk = chunk;
	}
	
	@Override
	public String toString() {
		String superString = "" + super.toString();
		return superString + " " + chunkId + "\n" + this.chunk;
	}
}
