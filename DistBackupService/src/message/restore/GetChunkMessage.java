package message.restore;

import java.util.ArrayList;

import message.general.Message;
import message.general.MessageConst;

public class GetChunkMessage extends Message {
	
	private int chunkId; 

	public GetChunkMessage(String version, int senderId, int fileId, int chunkId) {
		super(MessageConst.RESTORE_MESSAGE_TYPE, version, senderId, fileId);
		
		this.chunkId = chunkId;
	}	
	
	public GetChunkMessage(byte[] message) throws Error {
		super(message);
		
		String[] dividedHead = divideHead();
		if( dividedHead.length < 5 )
			throw new Error(MessageConst.MESSAGE_ERROR);
		
		chunkId = Integer.parseInt(dividedHead[4]);
	}
	
	
	public int getChunkId() {
		return chunkId;
	}

	public void setChunkId(int chunkId) {
		this.chunkId = chunkId;
	}

	public byte[] getHead() {
		if( head == null ) {
		
			ArrayList<Byte> message = new ArrayList<Byte>();
			byte[] sHead, mArr;
			
			sHead = super.getHead();
			
			addBytes(message, sHead);
			
			mArr = ("" + chunkId).getBytes();
			addBytes(message, mArr);
			addBytes(message, " ".getBytes());
			
			head = tobyte(message.toArray());
		
		}

		return head;
	}
}
