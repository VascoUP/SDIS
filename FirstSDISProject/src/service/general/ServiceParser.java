package service.general;

import information.MessageQueue;
import message.general.BasicMessage;
import message.general.MessageParser;
import message.general.QueueableMessage;

public class ServiceParser {	
	public static void parseService() {
		BasicMessage bm;
		QueueableMessage message = getNextQueuedMessage();
		
		if( message == null ) {
			//System.out.println("ServiceParser: Null message -> Error getting message");
			return ;
		}
		
		bm = parseMessage(message.getData());
		MessageToService.processMessage(message.getTime(), bm);
	}
	
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
}
