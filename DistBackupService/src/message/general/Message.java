package message.general;

import java.util.ArrayList;

public abstract class Message {


	protected final String version;
	protected final String messageType;
	protected final int senderId;
	protected final int fileId;
	
	protected byte[] body;
	protected byte[] head;
	
	public Message(String messageType, String version, int senderId, int fileId) {
		this.messageType = messageType;
		this.version = version;
		this.senderId = senderId;
		this.fileId = fileId;
		
		this.body = new byte[0];
	}
	
	public Message(byte[] message) {
		if(!parseMessage(message))
			throw new Error("Parser " + MessageConst.MESSAGE_ERROR);
		
		String[] dividedHead = divideHead();
		if( dividedHead.length < 4 )
			throw new Error(MessageConst.MESSAGE_ERROR);
			
		messageType = dividedHead[0];
		version = dividedHead[1];
		senderId = Integer.parseInt(dividedHead[2]);
		fileId = Integer.parseInt(dividedHead[3]);
	}
	
	
	
	public byte[] getHead() {
		if( head == null ) {
			ArrayList<Byte> message = new ArrayList<Byte>();
			byte[] mArr;
						
			mArr = messageType.getBytes();
			addBytes(message, mArr);
			addBytes(message, " ".getBytes());
			
			mArr = version.getBytes();
			addBytes(message, mArr);
			addBytes(message, " ".getBytes());
			
	
			mArr = ("" + senderId).getBytes();
			addBytes(message, mArr);
			addBytes(message, " ".getBytes());
			
			mArr = ("" + fileId).getBytes();
			addBytes(message, mArr);
			addBytes(message, " ".getBytes());
					
			head = tobyte(message.toArray());
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

	public int getFileId() {
		return fileId;
	}
	
	
	
	public byte[] getMessage() {
		byte[] message;
		byte[] h = getHead();
		byte[] b = getBody();
		
		message = new byte[h.length + b.length + 8];
		
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
		System.arraycopy(MessageConst.MESSAGE_FLAG, 0, 
				message, 6 + h.length + b.length, 2);
		
		return message;
	}
	
	public int getOffset() {
		return 0;
	}
	
	public int getLength() {
		return 64000;
	}

	
	
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
		
		for( ; messageIndex < message.length; messageIndex++ ) {
			tmp = parseFlag(message, messageIndex);
			if( tmp == messageIndex + 2 ) {
				//end of the head				
				
				if( type == MessageConst.STORE_TYPE_HEAD ) {
					
					head = new byte[messageIndex - 2 - index];
					System.arraycopy(message, index + 2, head, 0, messageIndex - 2 - index);

				} else if ( type == MessageConst.STORE_TYPE_BODY ) {
					
					body = new byte[messageIndex - 2 - index];
					System.arraycopy(message, index + 2, body, 0, messageIndex - 2 - index);

				}
				
				return tmp;
			}
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
	
	
	
	
	public static void addBytes(ArrayList<Byte> arrayList, byte[] mArr) {
		for( byte b : mArr )
			arrayList.add(b);		
	}
	
	public static byte[] tobyte(Object[] arrByte) {
		byte[] arrbyte = new byte[arrByte.length];
	    
		for( int i = 0; i < arrByte.length; i++ )
			arrbyte[i] = (Byte)arrByte[i];
		
		return arrbyte;
	}
	
	public static void printByteArray(byte[] arr) {
	    StringBuilder sb = new StringBuilder();
	    for (byte b : arr) {
	        sb.append(String.format("%02X ", b));
	    }
	    System.out.println(sb.toString());
	}
}