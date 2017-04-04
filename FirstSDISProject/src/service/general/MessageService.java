package service.general;

import message.BasicMessage;

public abstract class MessageService {
	public static void serve(long time, BasicMessage message) {
	}
	protected long time;
	
	protected BasicMessage message;	
	
	public MessageService(long time, BasicMessage message) {
		this.time = time;
		this.message = message;
	}
}