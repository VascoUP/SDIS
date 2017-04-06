package service;

import information.MessagesHashmap;
import message.BasicMessage;

public class MessageServiceChunk extends MessageService {
	public MessageServiceChunk(long time, BasicMessage message) {
		super(time, message);
	}
	
	public static void serve(long time, BasicMessage message) {
		MessageServiceChunk st = new MessageServiceChunk(time, message);
		st.notifySender();
	}
	
	public void notifySender() {
		MessagesHashmap.addMessage(message);
	}
}
