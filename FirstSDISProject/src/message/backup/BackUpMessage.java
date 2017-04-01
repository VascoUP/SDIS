package message.backup;

import java.util.ArrayList;

import information.PeerInfo;
import message.general.Message;
import message.general.MessageConst;
import message.general.MessageOperation;

public class BackUpMessage extends Message {
	private static final String MESSAGE_TYPE = MessageConst.PUTCHUNK_MESSAGE_TYPE;

	private int chunkId;

	public BackUpMessage(String version, int senderId, String fileId, int chunkId, byte[] chunk) {
		super(MESSAGE_TYPE, version, senderId, fileId);
		
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
		BackUpMessage m = (BackUpMessage) o;
		return 	m.getMessageType().equals(messageType) && 
				m.getFileId().equals(fileId) &&
				m.getChunkId() == chunkId;
	}

	public boolean isValidMessage() {
		return 	messageType.equals(MESSAGE_TYPE) &&
				senderId != PeerInfo.peerInfo.getServerID();
	}
}
