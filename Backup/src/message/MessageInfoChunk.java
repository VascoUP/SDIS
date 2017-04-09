package message;

/**
 * 
 * This class builds the CHUNK_MESSAGE information
 * This class extends the MessageInfo class
 *
 */
public class MessageInfoChunk extends MessageInfo {
	private final int chunkID;		//Chunk's ID
	private final byte[] chunk;		//Chunk's content
	
	/**
	 * MessageInfoChunk's constructor
	 * @param version Protocol's version
	 * @param senderID Sender's ID
	 * @param fileID File's ID
	 * @param chunkID Chunk's ID
	 * @param chunk Chunk's content
	 */
	public MessageInfoChunk(String version, int senderID, String fileID, int chunkID, byte[] chunk) {
		super(MessageConst.CHUNK_MESSAGE_TYPE, version, senderID, fileID);
		this.chunkID = chunkID;
		this.chunk = chunk;
	}
	
	/**
	 * Verifies if a message is equal to another
	 * @param o Object that will be compared
	 * @return true if the messages are equal, false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		MessageInfoChunk info = (MessageInfoChunk)o;
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
		arrayOfByteArrays[5] = chunk;
		
		return arrayOfByteArrays;
	}
	
	/**
	 * Gets the chunk's content
	 * @return The chunk's content
	 */
	public byte[] getChunk() {
		return chunk;
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
