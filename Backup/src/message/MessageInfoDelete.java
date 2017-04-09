package message;

/**
 * 
 * This class builds the DELETE_MESSAGE information
 * This class extends the MessageInfo class
 *
 */
public class MessageInfoDelete extends MessageInfo {
	/**
	 * MessageInfoDelete's constructor
	 * @param version Protocol's version
	 * @param senderID Sender's ID
	 * @param fileID File's ID
	 */
	public MessageInfoDelete(String version, int senderID, String fileID) {
		super(MessageConst.DELETE_MESSAGE_TYPE, version, senderID, fileID);
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
		
		arrayOfByteArrays[5] = new byte[0];
		
		return arrayOfByteArrays;
	}
	
	/**
	 * Gets the message's type and file's ID
	 * @return The message's type and file's ID
	 */
	@Override
	public String getName() {
		return super.getName();
	}
}
