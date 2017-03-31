package message.backup;

import java.util.ArrayList;

import information.PeerInfo;
import message.general.Message;
import message.general.MessageConst;

public class StoredMessage extends Message {
	private static final String MESSAGE_TYPE = MessageConst.STORED_MESSAGE_TYPE;

	private int chunkId; 

	public StoredMessage(String version, int senderId, String fileId, int chunkId) {
		super(MESSAGE_TYPE, version, senderId, fileId);
		
		this.chunkId = chunkId;
	}	
	
	public StoredMessage(byte[] message) throws Error {
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
		StoredMessage m = (StoredMessage) o;
		return 	m.getMessageType().equals(messageType) && 
				m.getFileId().equals(fileId) &&
				m.getChunkId() == chunkId;
	}
	
	public boolean isValidMessage() {
		return 	messageType.equals(MESSAGE_TYPE) &&
				senderId != PeerInfo.peerInfo.getServerID();
	}
}
