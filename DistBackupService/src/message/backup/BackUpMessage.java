package message.backup;

import message.Message;

public class BackUpMessage extends Message {

	private int chunkId;
	private byte[] chunk;

	public BackUpMessage(String version, int senderId, int fileId, int chunkId, byte[] chunk) {
		super(ChunkConst.ANSWER_MESSAGE_TYPE, version, senderId, fileId);
		
		this.chunkId = chunkId;
		this.chunk = chunk;
	}
	
	@Override
	public String toString() {
		String superString = "" + super.toString();
		return superString + " " + chunkId + "\n" + this.chunk;
	}
	
	public void setChunkID(int chunkID){
		this.chunkId = chunkID;
	}
	
	public void setChunkInformation(byte[] chunk){
		this.chunk = chunk;
	}
}
