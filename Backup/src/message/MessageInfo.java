package message;

/**
 * 
 * This class builds the message's information
 *
 */
public class MessageInfo {
	protected final String messageType;		//Message's type
	protected final String version;			//Protocol's version
	protected final int senderID;			//Sender's ID
	protected final String fileID;			//File's ID
	
	/**
	 * MessageInfo's constructor 
	 * @param messageType Message's type
	 * @param version Protocol's version
	 * @param senderID Sender's ID
	 * @param fileID File's ID
	 */
	public MessageInfo(String messageType, String version, int senderID, String fileID) {
		this.messageType = messageType;
		this.version = version;
		this.senderID = senderID;
		this.fileID = fileID;
	}

	/**
	 * Verifies if the message's information is equal to another one
	 * @param o Object that will be compared
	 * @return true if the messages are equal, false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		MessageInfo info = (MessageInfo)o;
		return 	info.getMessageType().equals(messageType) &&
				info.getFileID().equals(fileID);
	}

	/**
	 * Gets all the message's information
	 * @return A multidimensional byte array with the message's information
	 */
	public byte[][] getAll() {
		byte[][] arrayOfByteArrays = new byte[5][];
		arrayOfByteArrays[0] = messageType.getBytes();
		arrayOfByteArrays[1] = version.getBytes();
		arrayOfByteArrays[2] = ("" + senderID).getBytes();
		arrayOfByteArrays[3] = fileID.getBytes();
		arrayOfByteArrays[4] = new byte[0]; //body
		
		return arrayOfByteArrays;
	}

	/**
	 * Gets the file's ID
	 * @return The file's ID
	 */
	public String getFileID() {
		return fileID;
	}

	/**
	 * Gets the message's type
	 * @return The message's type
	 */
	public String getMessageType() {
		return messageType;
	}
	
	/**
	 * Gets the message's type and file's ID
	 * @return The message's type and file's ID
	 */
	public String getName() {
		return messageType + fileID;
	}
	
	/**
	 * Gets the sender's ID
	 * @return The sender's ID
	 */
	public int getSenderID() {
		return senderID;
	}

	/**
	 * Gets the protocol's version
	 * @return The protocol's version
	 */
	public String getVersion() {
		return version;
	}
}
