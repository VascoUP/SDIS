package information;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import message.QueueableMessage;

/**
 * 
 * This class builds a queue message
 *
 */
public class MessageQueue {
	private static BlockingQueue<QueueableMessage> messageQueue = new LinkedBlockingQueue<QueueableMessage>(64); //Creates a BlockingQueue with the queueable messages
	
	/**
	 * Adds a queueable message to the BlockingQueue
	 * @param message Message that will be added
	 */
	public static void put(byte[] message) {
		long time = System.currentTimeMillis(); 				//Current time in milliseconds
		messageQueue.add(new QueueableMessage(time, message));  //Adds the queueable message to the BlockingQueue
	}
	
	/**
	 * Takes the queueable message
	 * @return The queueable message
	 * @throws InterruptedException Thrown when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity
	 */
	public static QueueableMessage take() throws InterruptedException {
		return messageQueue.take();
	}
}
