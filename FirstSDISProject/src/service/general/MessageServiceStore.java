package service.general;

import information.MessagesHashmap;
import message.BasicMessage;
import message.MessageToString;

public class MessageServiceStore extends MessageService {
	public static void serve(long time, BasicMessage message) {
		MessageServiceStore st = new MessageServiceStore(time, message);
		st.notifySender();
	}
	
	public MessageServiceStore(long time, BasicMessage message) {
		super(time, message);
	}
	
	public void notifySender() {		
		MessagesHashmap.addMessage(MessageToString.getName(message));
	}
}
