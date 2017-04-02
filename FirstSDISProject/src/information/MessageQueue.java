package information;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {
	private static BlockingQueue<byte[]> messageQueue = new LinkedBlockingQueue<byte[]>(64);

	public static BlockingQueue<byte[]> getMessageQueue() {
		return messageQueue;
	}
}
