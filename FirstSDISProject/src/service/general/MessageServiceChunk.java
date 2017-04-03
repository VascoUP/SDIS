package service.general;

import message.BasicMessage;

public class MessageServiceChunk extends MessageService {
	public MessageServiceChunk(long time, BasicMessage message) {
		super(time, message);
	}
	
	public static void serve(long time, BasicMessage message) {
	}
}
