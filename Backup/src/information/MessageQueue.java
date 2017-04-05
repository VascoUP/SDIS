package information;

import message.QueueableMessage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {
	private static final BlockingQueue<QueueableMessage> messageQueue = new LinkedBlockingQueue<>( 64 );
	
	public static void put(byte[] message) {
		long time = System.currentTimeMillis();
		messageQueue.add(new QueueableMessage(time, message));
	}
	
	public static QueueableMessage take() throws InterruptedException {
		return messageQueue.take();
	}
}
