package message.restore;

import message.Message;
import message.MessageConst;

public class GetChunkMessage extends Message {
	
	private int chunkId; 

	public GetChunkMessage(String version, int senderId, int fileId, int chunkId) {
		super(MessageConst.REQUEST_MESSAGE_TYPE, version, senderId, fileId);
		
		this.chunkId = chunkId;
	}
	
	@Override
	public String toString() {
		String superString = "" + super.toString();
		return superString + " " + chunkId;
	}
}
