package message;

/**
 * 
 * This class builds the RESTORE_MESSAGE information
 * This class extends the MessageInfo class
 *
 */
public class MessageInfoGetChunk extends MessageInfo {
	private final int chunkID;	//Chunk's ID
	
	/**
	 * MessageInfoGetChunk's constructor
	 * @param version Protocol's version
	 * @param senderID Sender's ID
	 * @param fileID File's ID
	 * @param chunkID Chunk's ID
	 */
	public MessageInfoGetChunk(String version, int senderID, String fileID, int chunkID) {
		super(MessageConst.RESTORE_MESSAGE_TYPE, version, senderID, fileID);
		this.chunkID = chunkID;
	}

	/**
	 * Verifies if the message's information is equal to another one
	 * @param o Object that will be compared
	 * @return true if the messages are equal, false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		MessageInfoGetChunk info = (MessageInfoGetChunk)o;
		return 	super.equals(o) && 
				info.getChunkID() == chunkID;
	}
	
	/**
	 * Gets a array of byte's array with all the message's information
	 * @return A array of byte's array with all the message's information
	 */
	@Override
	public byte[][] getAll() {
		byte[][] arrayOfByteArrays = new byte[6][];
		byte[][] superResult = super.getAll();
		
		for( int i = 0; i < superResult.length; i++ )
			arrayOfByteArrays[i] = superResult[i];
		
		arrayOfByteArrays[4] = ("" + chunkID).getBytes();
		arrayOfByteArrays[5] = new byte[0];
		
		return arrayOfByteArrays;
	}
	
	/**
	 * Gets the chunk's ID
	 * @return The chunk's ID
	 */
	public int getChunkID() {
		return chunkID;
	}
	
	/**
	 * Gets the message's type, file's ID and chunk's ID
	 * @return The message's type, file's ID and chunk's ID
	 */
	@Override
	public String getName() {
		return super.getName() + chunkID;
	}
}
