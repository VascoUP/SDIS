package service.general;

import information.MessagesHashmap;
import message.BasicMessage;

public class MessageServiceStore extends MessageService {
	public MessageServiceStore(long time, BasicMessage message) {
		super(time, message);
	}
	
	public void notifySender() {
		String[] mHead = message.getHead();
		
		String messageType = mHead[0];
		String fileID = mHead[3];
		String chunkID = mHead[4];
		
		MessagesHashmap.addMessage(messageType + fileID + chunkID);
	}
	
	public static void serve(long time, BasicMessage message) {
		MessageServiceStore st = new MessageServiceStore(time, message);
		st.notifySender();
	}
}
