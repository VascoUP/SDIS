package message;

/**
 * 
 * This class builds the PUTCHUNK_MESSAGE information
 * This class extends the MessageInfo class
 *
 */
public class MessageInfoPutChunk extends MessageInfo {
	private final int chunkID;					//Chunk's ID
	private final int replication_degree;		//Replication's degree
	private final byte[] chunk;					//Chunk's content
	
	/**
	 * MessageInfoPutChunk's constructor
	 * @param version Protocol's version
	 * @param senderID Sender's ID
	 * @param fileID File's ID
	 * @param chunkID Chunk's ID
	 * @param replication_degree Replication's degree
	 * @param chunk Chunk's content
	 */
	public MessageInfoPutChunk(String version, int senderID, String fileID, int chunkID, int replication_degree, byte[] chunk) {
		super(MessageConst.PUTCHUNK_MESSAGE_TYPE, version, senderID, fileID);
		
		this.chunkID = chunkID;
		this.replication_degree = replication_degree;
		this.chunk = chunk;
	}

	/**
	 * Verifies if the message's information is equal to another one
	 * @param o Object that will be compared
	 * @return true if the messages are equal, false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		MessageInfoPutChunk info = (MessageInfoPutChunk)o;
		return 	super.equals(o) && 
				info.getChunkID() == chunkID;
	}

	/**
	 * Gets a array of byte's array with all the message's information
	 * @return A array of byte's array with all the message's information
	 */
	@Override
	public byte[][] getAll() {
		byte[][] arrayOfByteArrays = new byte[7][];
		byte[][] superResult = super.getAll();
		
		for( int i = 0; i < superResult.length; i++ )
			arrayOfByteArrays[i] = superResult[i];
		
		arrayOfByteArrays[4] = ("" + chunkID).getBytes();
		arrayOfByteArrays[5] = ("" + replication_degree).getBytes();
		arrayOfByteArrays[6] = chunk;
		
		return arrayOfByteArrays;
	}
	
	/**
	 * Gets the chunk's content
	 * @return The chunks's content
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

	/**
	 * Gets the replication's degree
	 * @return The replication's degree
	 */
	public int getReplicationDegree() {
		return replication_degree;
	}
}
