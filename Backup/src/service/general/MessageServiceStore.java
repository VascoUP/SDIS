package service.general;

import information.MessagesHashmap;
import message.BasicMessage;
import message.MessageToString;

class MessageServiceStore extends MessageService {
	private MessageServiceStore(long time, BasicMessage message) {
		super(time, message);
	}

	public static void serve(long time, BasicMessage message) {
		MessageServiceStore st = new MessageServiceStore(time, message);
		st.notifySender();
	}
	
	private void notifySender() {
		MessagesHashmap.addMessage(MessageToString.getName(message));
	}
}
