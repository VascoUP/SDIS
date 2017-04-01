package information;

import datastructure.BlockerQueue;

public class MessageQueue {
	private static BlockerQueue<byte[]> messageQueue = new BlockerQueue<byte[]>();

	public static BlockerQueue<byte[]> getMessageQueue() {
		return messageQueue;
	}
}
