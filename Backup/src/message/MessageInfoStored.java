package message;

public class MessageInfoStored extends MessageInfo {
	private final int chunkID;
	
	public MessageInfoStored(String version, int senderID, String fileID, int chunkID) {
		super(MessageConst.STORED_MESSAGE_TYPE, version, senderID, fileID);
		this.chunkID = chunkID;
	}
	
	@Override
	public boolean equals(Object o) {
		MessageInfoStored info = (MessageInfoStored)o;
		return 	super.equals(o) && 
				info.getChunkID() == chunkID;
	}
	
	@Override
	public byte[][] getAll() {
		byte[][] arrayOfByteArrays = new byte[6][];
		byte[][] superResult = super.getAll();

        System.arraycopy( superResult, 0, arrayOfByteArrays, 0, superResult.length );
		
		arrayOfByteArrays[4] = ("" + chunkID).getBytes();
		arrayOfByteArrays[5] = new byte[0];
		
		return arrayOfByteArrays;
	}
	
	private int getChunkID() {
		return chunkID;
	}
	
	@Override
	public String getName() {
		return super.getName() + chunkID;
	}
}
