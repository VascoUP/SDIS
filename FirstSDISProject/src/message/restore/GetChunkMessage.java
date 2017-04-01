package message.restore;

import java.util.ArrayList;

import message.general.Message;
import message.general.MessageConst;
import message.general.MessageOperation;
import information.PeerInfo;

public class GetChunkMessage extends Message {
	private static final String MESSAGE_TYPE = MessageConst.RESTORE_MESSAGE_TYPE;
	
	private int chunkId; 

	public GetChunkMessage(String version, int senderId, String fileId, int chunkId) {
		super(MESSAGE_TYPE, version, senderId, fileId);
		
		this.chunkId = chunkId;
	}	
	
	public GetChunkMessage(byte[] message) throws Error {
		super(message);
		
		String[] dividedHead = divideHead();
		if( dividedHead.length < 5 )
			throw new Error(MessageConst.MESSAGE_ERROR);
		
		chunkId = Integer.parseInt(dividedHead[4]);
		System.out.println("GtCkMsg " + chunkId);
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
			
			MessageOperation.addBytes(message, sHead);
			
			mArr = ("" + chunkId).getBytes();
			MessageOperation.addBytes(message, mArr);
			MessageOperation.addBytes(message, " ".getBytes());
			
			head = MessageOperation.tobyte(message.toArray());
		
		}

		return head;
	}

	
	/*=============
	 * COMPARATORS
	 *=============
	 */
	@Override
	public boolean equals(Object o) {
		GetChunkMessage m = (GetChunkMessage) o;
		return 	m.getMessageType().equals(messageType) && 
				m.getFileId().equals(fileId) &&
				m.getChunkId() == chunkId;
	}
	
	public boolean isValidMessage() {
		return 	messageType.equals(MESSAGE_TYPE) &&
				senderId != PeerInfo.peerInfo.getServerID();
	}
}
