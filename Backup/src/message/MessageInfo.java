package message;

public class MessageInfo {
	protected final String messageType;
	protected final String version;
	protected final int senderID;
	protected final String fileID;
	
	public MessageInfo(String messageType, String version, int senderID, String fileID) {
		this.messageType = messageType;
		this.version = version;
		this.senderID = senderID;
		this.fileID = fileID;
	}

	@Override
	public boolean equals(Object o) {
		MessageInfo info = (MessageInfo)o;
		return 	info.getMessageType().equals(messageType) &&
				info.getFileID().equals(fileID);
	}

	public byte[][] getAll() {
		byte[][] arrayOfByteArrays = new byte[5][];
		arrayOfByteArrays[0] = messageType.getBytes();
		arrayOfByteArrays[1] = version.getBytes();
		arrayOfByteArrays[2] = ("" + senderID).getBytes();
		arrayOfByteArrays[3] = fileID.getBytes();
		arrayOfByteArrays[4] = new byte[0]; //body
		
		return arrayOfByteArrays;
	}

	public String getFileID() {
		return fileID;
	}

	public String getMessageType() {
		return messageType;
	}
	
	public String getName() {
		return messageType + fileID;
	}
	
	public int getSenderID() {
		return senderID;
	}

	public String getVersion() {
		return version;
	}
}
