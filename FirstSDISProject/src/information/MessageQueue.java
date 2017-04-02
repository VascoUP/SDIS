package information;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import message.general.QueueableMessage;

public class MessageQueue {
	private static BlockingQueue<QueueableMessage> messageQueue = new LinkedBlockingQueue<QueueableMessage>(64);
	
	public static void put(byte[] message) {
		long time = System.currentTimeMillis();
		messageQueue.add(new QueueableMessage(time, message));
	}
	
	public static QueueableMessage take() throws InterruptedException {
		return messageQueue.take();
	}
}
