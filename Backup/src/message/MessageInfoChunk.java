package message;

public class MessageInfoChunk extends MessageInfo {
	private final int chunkID;
	private final byte[] chunk;
	
	public MessageInfoChunk(String version, int senderID, String fileID, int chunkID, byte[] chunk) {
		super(MessageConst.CHUNK_MESSAGE_TYPE, version, senderID, fileID);
		this.chunkID = chunkID;
		this.chunk = chunk;
	}
	
	@Override
	public boolean equals(Object o) {
		MessageInfoChunk info = (MessageInfoChunk)o;
		return 	super.equals(o) && 
				info.getChunkID() == chunkID;
	}
	
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
	
	public byte[] getChunk() {
		return chunk;
	}
	
	public int getChunkID() {
		return chunkID;
	}

	@Override
	public String getName() {
		return super.getName() + chunkID;
	}
}
