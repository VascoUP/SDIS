package message;

public class MessageInfoPutChunk extends MessageInfo {
	private final int chunkID;
	private final int replication_degree;
	private final byte[] chunk;
	
	public MessageInfoPutChunk(String version, int senderID, String fileID, int chunkID, int replication_degree, byte[] chunk) {
		super(MessageConst.PUTCHUNK_MESSAGE_TYPE, version, senderID, fileID);
		
		this.chunkID = chunkID;
		this.replication_degree = replication_degree;
		System.out.println("MessageInfoPutChunk: " + chunk.length);
		this.chunk = chunk;
	}

	@Override
	public boolean equals(Object o) {
		MessageInfoPutChunk info = (MessageInfoPutChunk)o;
		return 	super.equals(o) && 
				info.getChunkID() == chunkID;
	}

	@Override
	public byte[][] getAll() {
		byte[][] arrayOfByteArrays = new byte[7][];
		byte[][] superResult = super.getAll();

        System.arraycopy( superResult, 0, arrayOfByteArrays, 0, superResult.length );
		
		arrayOfByteArrays[4] = ("" + chunkID).getBytes();
		arrayOfByteArrays[5] = ("" + replication_degree).getBytes();
		arrayOfByteArrays[6] = chunk;
		
		return arrayOfByteArrays;
	}

    public int getChunkID() {
		return chunkID;
	}
	
	@Override
	public String getName() {
		return super.getName() + chunkID;
	}

	public int getReplication_degree() {
		return replication_degree;
	}
}
