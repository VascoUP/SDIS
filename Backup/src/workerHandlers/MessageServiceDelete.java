package workerHandlers;

import information.FileInfo;
import message.BasicMessage;
import message.MessageInfoDelete;
import message.MessageToInfo;

/**
 * 
 * This class builds the message's service for delete
 * This extends the MessageService class
 *
 */
public class MessageServiceDelete extends MessageService {
	/**
	 * MessageServiceDelete's constructor
	 * @param time Message service's time
	 * @param message Basic Message
	 */
	public MessageServiceDelete(long time, BasicMessage message) {
		super(time, message);
	}
	
	/**
	 * Creates a new message's service and deletes the files
	 * @param time Message service's time
	 * @param message Basic message
	 */
	public static void serve(long time, BasicMessage message) {
		MessageServiceDelete sd = new MessageServiceDelete(time, message);
		sd.deleteFiles();
	}
	
	/**
	 * Deletes the stored files
	 */
	private void deleteFiles() {
		MessageInfoDelete info = (MessageInfoDelete) MessageToInfo.messageToInfo(message);
		FileInfo.eliminateStoredFile(info.getFileID());
	}
}
