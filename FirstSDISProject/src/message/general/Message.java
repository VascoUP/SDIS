package message.general;

import java.util.ArrayList;

public abstract class Message {
	protected final String version;
	protected final String messageType;
	protected final int senderId;
	protected final String fileId;
	
	protected byte[] body;
	protected byte[] head;
	
	public Message(String messageType, String version, int senderId, String fileId) {
		this.messageType = messageType;
		this.version = version;
		this.senderId = senderId;
		this.fileId = fileId;
		
		this.body = new byte[0];
	}
	
	public Message(byte[] message) {
		message = MessageOperation.trim(message);
		
		if(!parseMessage(message))
			throw new Error("Parser " + MessageConst.MESSAGE_ERROR);
		
		String[] dividedHead = divideHead();
		if( dividedHead.length < 4 )
			throw new Error(MessageConst.MESSAGE_ERROR);
			
		messageType = dividedHead[0];
		version = dividedHead[1];
		senderId = Integer.parseInt(dividedHead[2]);
		fileId = dividedHead[3];
	}
	
	
	/*=====================
	 * GETTERS AND SETTERS
	 *=====================
	 */
	public byte[] getHead() {
		if( head == null ) {
			ArrayList<Byte> message = new ArrayList<Byte>();
			byte[] mArr;
						
			mArr = messageType.getBytes();
			MessageOperation.addBytes(message, mArr);
			MessageOperation.addBytes(message, " ".getBytes());
			
			mArr = version.getBytes();
			MessageOperation.addBytes(message, mArr);
			MessageOperation.addBytes(message, " ".getBytes());
			
	
			mArr = ("" + senderId).getBytes();
			MessageOperation.addBytes(message, mArr);
			MessageOperation.addBytes(message, " ".getBytes());
			
			mArr = fileId.getBytes();
			MessageOperation.addBytes(message, mArr);
			MessageOperation.addBytes(message, " ".getBytes());
					
			head = MessageOperation.tobyte(message.toArray());
		}
		
		return head;
	}
	
	public byte[] getBody() {
		return body;
	}
	
	public void setBody( byte[] b ) {
		body = b;
	}
	
	public String getVersion() {
		return version;
	}

	public String getMessageType() {
		return messageType;
	}

	public int getSenderId() {
		return senderId;
	}

	public String getFileId() {
		return fileId;
	}
		
	public byte[] getMessage() {
		byte[] message;
		byte[] h = getHead();
		byte[] b = getBody();
				
		message = new byte[h.length + b.length + 6];
		
		System.arraycopy(MessageConst.MESSAGE_FLAG, 0, 
				message, 0, 2);
		System.arraycopy(h, 0, 
				message, 2, h.length);
		System.arraycopy(MessageConst.MESSAGE_FLAG, 0, 
				message, 2 + h.length, 2);
		System.arraycopy(MessageConst.MESSAGE_FLAG, 0, 
				message, 4 + h.length, 2);
		System.arraycopy(b, 0, 
				message, 6 + h.length, b.length);

		parseMessage(message);
				
		return message;
	}

	
	/*=============
	 * COMPARATORS
	 *=============
	 */
	@Override
	public boolean equals(Object o) {
		Message m = (Message) o;
		return 	m.getMessageType().equals(messageType) && 
				m.getFileId().equals(fileId);
	}
	
	public boolean isValidMessage() {
		return false;
	}
	
	
	/*=========
	 * PARSERS
	 *=========
	 */
	public int parseFlag(byte[] message, int index) {
		for( int i = 0; i < MessageConst.MESSAGE_FLAG.length && index < message.length; i++, index++ ) {
			if( MessageConst.MESSAGE_FLAG[i] != message[index] ) 
				break;
		}
		return index;
	}
		
	public int parsePart(byte[] message, int index, int type) {
		int messageIndex = index, tmp;
		
		tmp = parseFlag(message, messageIndex);
		if( tmp != messageIndex + 2 ) 
			return -1;
		messageIndex = tmp;
		

		if( type == MessageConst.STORE_TYPE_HEAD ) {
			
			for( ; messageIndex < message.length; messageIndex++ ) {
				tmp = parseFlag(message, messageIndex);
				if( tmp == messageIndex + 2 ) {
					//end of the head				
					head = new byte[messageIndex - (2 + index)];
					System.arraycopy(message, index + 2, head, 0, messageIndex - (2 + index));
					return tmp;
				}
			}
			
		} else if ( type == MessageConst.STORE_TYPE_BODY ) {
		
			byte[] b2 = new byte[message.length - (index + 2)];
			System.arraycopy(message, index + 2, b2, 0, message.length - (2 + index));
			body = MessageOperation.trim(b2);
			return tmp;
				
		}
		
		return -1;
	}

	public boolean parseMessage(byte[] message) {
		int messageIndex = 0;
				
		messageIndex = parsePart(message, messageIndex, MessageConst.STORE_TYPE_HEAD);
		if( messageIndex < 0 )
			return false;
		
		
		messageIndex = parsePart(message, messageIndex, MessageConst.STORE_TYPE_BODY);
		
		return messageIndex > 0;
	}
	
	public String[] divideHead() {
		String headStr = new String(head);
		return headStr.split(" ");		
	}
	
	

}