package message;

public class Message {
	private String version;
	private String messageType;
	private int senderId;
	private int fileId;
	
	public Message(String messageType, String version, int senderId, int fileId) {
		this.messageType = messageType;
		this.version = version;
		this.senderId = senderId;
		this.fileId = fileId;
	}
	
	public int getOffset() {
		return 0;
	}
	
	public int getLength() {
		return 64000;
	}
	
	public byte[] getMessage() {
		return ("" + this).getBytes();
	}
	
	@Override
	public String toString() {
		return messageType + " " + version + " " + senderId + " " + fileId;
	}
}