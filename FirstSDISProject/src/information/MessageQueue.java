package information;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import message.QueueableMessage;

public class MessageQueue {
	private static BlockingQueue<QueueableMessage> messageQueue = new LinkedBlockingQueue<QueueableMessage>(64);
	
	public static synchronized void put(byte[] message) {
		long time = System.currentTimeMillis();
		messageQueue.add(new QueueableMessage(time, message));
	}
	
	public static synchronized QueueableMessage take() throws InterruptedException {
		return messageQueue.take();
	}
}
