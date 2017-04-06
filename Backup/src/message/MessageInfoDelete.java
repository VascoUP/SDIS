package message;

public class MessageInfoDelete extends MessageInfo {
	public MessageInfoDelete(String version, int senderID, String fileID) {
		super(MessageConst.DELETE_MESSAGE_TYPE, version, senderID, fileID);
	}
	
	@Override
	public byte[][] getAll() {
		byte[][] arrayOfByteArrays = new byte[6][];
		byte[][] superResult = super.getAll();
		
		for( int i = 0; i < superResult.length; i++ )
			arrayOfByteArrays[i] = superResult[i];
		
		arrayOfByteArrays[5] = new byte[0];
		
		return arrayOfByteArrays;
	}
	
	@Override
	public String getName() {
		return super.getName();
	}
}
