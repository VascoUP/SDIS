package workerHandlers;

import information.MessagesHashmap;
import message.BasicMessage;

/**
 * 
 * This class builds the message's service for chunk
 * This extends the MessageService class
 *
 */
public class MessageServiceChunk extends MessageService {
	/**
	 * MessageServiceChunk's constructor
	 * @param time Message service's time
	 * @param message Basic Message
	 */
	public MessageServiceChunk(long time, BasicMessage message) {
		super(time, message);
	}
	
	/**
	 * Creates a new message's service and notify the sender
	 * @param time Message service's time
	 * @param message Basic message
	 */
	public static void serve(long time, BasicMessage message) {
		MessageServiceChunk st = new MessageServiceChunk(time, message);
		st.notifySender();
	}
	
	/**
	 * Notifies the sender adding a message to the message hashmap
	 */
	public void notifySender() {
		MessagesHashmap.addMessage(message);
	}
}
