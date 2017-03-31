package message.restore;

import java.util.ArrayList;

import message.general.Message;
import message.general.MessageConst;
import ui.App;

public class ChunkMessage extends Message {
	private static final String MESSAGE_TYPE = MessageConst.CHUNK_MESSAGE_TYPE;
	
	private int chunkId; 

	public ChunkMessage(String version, int senderId, String fileId, int chunkId, byte[] chunk) {
		super(MESSAGE_TYPE, version, senderId, fileId);
		
		this.chunkId = chunkId;
		this.body = chunk;
	}
	
	public ChunkMessage(byte[] message) throws Error {
		super(message);
		
		String[] dividedHead = divideHead();
		if( dividedHead.length < 5 )
			throw new Error(MessageConst.MESSAGE_ERROR);
		
		chunkId = Integer.parseInt(dividedHead[4]);
	}
	
	
	/*=====================
	 * GETTERS AND SETTERS
	 *=====================
	 */
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
	
	
	/*=============
	 * COMPARATORS
	 *=============
	 */
	@Override
	public boolean equals(Object o) {
		ChunkMessage m = (ChunkMessage) o;
		return 	m.getMessageType().equals(messageType) && 
				m.getFileId().equals(fileId) &&
				m.getChunkId() == chunkId;
	}

	public boolean isValidMessage() {
		return 	messageType.equals(MESSAGE_TYPE) &&
				senderId != App.getServerId();
	}
}
