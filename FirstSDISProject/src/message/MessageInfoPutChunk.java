package message;

public class MessageInfoPutChunk extends MessageInfo {
	private final int chunkID;
	private final int replication_degree;
	private final byte[] chunk;
	
	public MessageInfoPutChunk(String version, int senderID, String fileID, int chunkID, int replication_degree, byte[] chunk) {
		super(MessageConst.PUTCHUNK_MESSAGE_TYPE, version, senderID, fileID);
		
		this.chunkID = chunkID;
		this.replication_degree = replication_degree;
		this.chunk = chunk;
	}

	public int getChunkID() {
		return chunkID;
	}

	public int getReplication_degree() {
		return replication_degree;
	}
	
	public byte[] getChunk() {
		return chunk;
	}
	
	public byte[][] getAll() {
		byte[][] arrayOfByteArrays = new byte[8][];
		byte[][] superResult = super.getAll();
		
		for( int i = 0; i < superResult.length; i++ )
			arrayOfByteArrays[i] = superResult[i];
		
		arrayOfByteArrays[4] = ("" + chunkID).getBytes();
		arrayOfByteArrays[5] = ("" + replication_degree).getBytes();
		arrayOfByteArrays[6] = chunk;
		
		return arrayOfByteArrays;
	}
	
	@Override
	public boolean equals(Object o) {
		MessageInfoPutChunk info = (MessageInfoPutChunk)o;
		return 	super.equals(o) && 
				info.getChunkID() == chunkID;
	}

	public String getName() {
		return super.getName() + chunkID;
	}
}