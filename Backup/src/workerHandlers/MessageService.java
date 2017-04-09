package workerHandlers;

import message.BasicMessage;

/**
 * 
 * This class builds the message's service
 *
 */
public abstract class MessageService {
	protected long time;				//Message service's time
	protected BasicMessage message;		//Basic message
	
	/**
	 * MessageService's constructor
	 * @param time Message service's time
	 * @param message Basic message
	 */
	public MessageService(long time, BasicMessage message) {
		this.time = time;
		this.message = message;
	}

	/**
	 * Creates a new service
	 * @param time Message service's time
	 * @param message Basic message
	 */
	public static void serve(long time, BasicMessage message) {
	}
}