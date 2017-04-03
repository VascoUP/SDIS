package message;

public class MessageInfoGetChunk extends MessageInfo {
	private final int chunkID;
	
	public MessageInfoGetChunk(String version, int senderID, String fileID, int chunkID) {
		super(MessageConst.RESTORE_MESSAGE_TYPE, version, senderID, fileID);
		this.chunkID = chunkID;
	}

	public int getChunkID() {
		return chunkID;
	}
	
	public byte[][] getAll() {
		byte[][] arrayOfByteArrays = new byte[6][];
		byte[][] superResult = super.getAll();
		
		for( int i = 0; i < superResult.length; i++ )
			arrayOfByteArrays[i] = superResult[i];
		
		arrayOfByteArrays[4] = ("" + chunkID).getBytes();
		arrayOfByteArrays[5] = new byte[0];
		
		return arrayOfByteArrays;
	}
	
	
	@Override
	public boolean equals(Object o) {
		MessageInfoGetChunk info = (MessageInfoGetChunk)o;
		return 	super.equals(o) && 
				info.getChunkID() == chunkID;
	}
}
