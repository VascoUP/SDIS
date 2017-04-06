package message;

public class MessageToString {
	public static String getName(BasicMessage message) {
		String[] mHead = message.getHead();
		String messageType = mHead[0];
		String fileID = mHead[3];
		String chunkID = mHead[4];
		return messageType + fileID + chunkID;
	}
	
	public static String getName(MessageInfo info) {
		return info.getName();
	}
}
