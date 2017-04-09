package message;

/**
 * 
 * This class converts a basic message into a string
 *
 */
public class MessageToString {
	/**
	 * Gets the message's type, file's ID and chunk's ID from the basic message's header
	 * @param message Basic message that will be analyzed
	 * @return The message's type, file's ID and chunk's ID from the basic message's header
	 */
	public static String getName(BasicMessage message) {
		String[] mHead = message.getHead();
		String messageType = mHead[0];
		String fileID = mHead[3];
		String chunkID = mHead[4];
		return messageType + fileID + chunkID;
	}
	
	/**
	 * Gets the MessageInfo's name
	 * @param info MessageInfo that will be used to know the name
	 * @return The MessageInfo's name
	 */
	public static String getName(MessageInfo info) {
		return info.getName();
	}
}
