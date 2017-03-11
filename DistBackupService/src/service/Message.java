package service;

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
	
	@Override
	public String toString() {
		return messageType + " " + version + " " + senderId + " " + fileId;
	}
}