package service.general;

import message.BasicMessage;

abstract class MessageService {
	final long time;
	final BasicMessage message;
	
	MessageService(long time, BasicMessage message) {
		this.time = time;
		this.message = message;
	}

}