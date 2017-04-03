package service.general;

import message.BasicMessage;

public class WaitStoreChunk extends MessageServiceWait {
	public WaitStoreChunk(long time, BasicMessage message) {
		super(time, message);
	}
	
	public boolean condition() {
		// Check and see if the replication degree was already reached
		return false;
	}
	
	public void service() {
		/* Store the chunk
		 * Send, to the MC channel, the stored message
		 */
		System.err.println("WaitMessageService: service wrong class");
	}
	
	public static void serve(long time, BasicMessage message) {
		WaitStoreChunk sc = new WaitStoreChunk(time, message);
		sc.start();
	}
}