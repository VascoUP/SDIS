package workerHandlers;

import information.MessageQueue;
import information.MessagesHashmap;
import message.BasicMessage;
import message.MessageParser;
import message.QueueableMessage;

/**
 * 
 * This class parses a service
 *
 */
public class ServiceParser {
	/**
	 * Gets the next queued message
	 * @return The next queueable message
	 */
	public static QueueableMessage getNextQueuedMessage() {
		try {
			return MessageQueue.take();
		} catch (InterruptedException e) {
			return null;
		}
	}
	
	/**
	 * Parses the message's byte's array 
	 * @param message Message that will be parsed
	 * @return The basic message after the parsing operation
	 */
	public static BasicMessage parseMessage(byte[] message) {
		return MessageParser.parseMessage(message);
	}
	
	/**
	 * Parses a service
	 */
	public static void parseService() {
		BasicMessage bm;
		QueueableMessage message = getNextQueuedMessage();
		if( message == null )
			return ;
		
		bm = parseMessage(message.getData());
		if( bm == null )
			return ;
		
		MessagesHashmap.addMessage(bm);
		MessageToService.processMessage(message.getTime(), bm);
	}
}
