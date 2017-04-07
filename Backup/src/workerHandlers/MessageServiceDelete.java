package workerHandlers;

import information.FileInfo;
import message.BasicMessage;
import message.MessageInfoDelete;
import message.MessageToInfo;

public class MessageServiceDelete extends MessageService {
	public MessageServiceDelete(long time, BasicMessage message) {
		super(time, message);
	}
	
	public static void serve(long time, BasicMessage message) {
		MessageServiceDelete sd = new MessageServiceDelete(time, message);
		sd.deleteFiles();
	}
	
	private void deleteFiles() {
		MessageInfoDelete info = (MessageInfoDelete) MessageToInfo.messageToInfo(message);
		FileInfo.eliminateStoredFile(info.getFileID());
	}
}
