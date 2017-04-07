package workerHandlers;

import information.MessageQueue;
import message.BasicMessage;
import message.MessageParser;
import message.QueueableMessage;

public class ServiceParser {	
	public static QueueableMessage getNextQueuedMessage() {
		try {
			return MessageQueue.take();
		} catch (InterruptedException e) {
			return null;
		}
	}
	
	public static BasicMessage parseMessage(byte[] message) {
		return MessageParser.parseMessage(message);
	}
	
	public static void parseService() {
		BasicMessage bm;
		QueueableMessage message = getNextQueuedMessage();

		if( message == null ) {
			return ;
		}
		
		bm = parseMessage(message.getData());
		MessageToService.processMessage(message.getTime(), bm);
	}
}
