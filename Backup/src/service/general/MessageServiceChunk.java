package service.general;

import information.MessagesHashmap;
import message.BasicMessage;
import message.MessageToString;

public class MessageServiceChunk extends MessageService {
	public MessageServiceChunk(long time, BasicMessage message) {
		super(time, message);
	}
	
	public static void serve(long time, BasicMessage message) {
		MessageServiceChunk st = new MessageServiceChunk(time, message);
		st.notifySender();
	}
	
	public void notifySender() {
		// This needs to change
		// Send chunk to the hasmap (????????)
		int i = 0;
		MessagesHashmap.addMessage(MessageToString.getName(message));
	}
}
