package message.backup;

import java.util.ArrayList;

import message.general.Message;
import message.general.MessageConst;

public class BackUpMessage extends Message {

	private int chunkId;

	public BackUpMessage(String version, int senderId, int fileId, int chunkId, byte[] chunk) {
		super(MessageConst.PUTCHUNK_MESSAGE_TYPE, version, senderId, fileId);
		
		this.chunkId = chunkId;
		this.body = chunk;
	}
	
	public BackUpMessage(byte[] message) throws Error {
		super(message);
		
		String[] dividedHead = divideHead();
		if( dividedHead.length < 5 )
			throw new Error("BackUp " + MessageConst.MESSAGE_ERROR);
		
		chunkId = Integer.parseInt(dividedHead[4]);
	}
	
	
	
	public int getChunkId() {
		return chunkId;
	}

	public void setChunkId(int chunkId) {
		this.chunkId = chunkId;
	}
	
	public void setChunkInformation(byte[] chunk){
		setBody(chunk);
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
