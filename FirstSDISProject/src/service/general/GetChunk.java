package service.general;

import message.BasicMessage;

public class GetChunk extends WaitMessageService {
	public GetChunk(long time, BasicMessage message) {
		super(time, message);
	}
	
	public boolean condition() {
		// Check and see if there were already answers 
		return false;
	}
	
	public void service() {
		/* Get requested chunk
		 * Send, to the MDR channel, the chunk message
		 */
		System.err.println("WaitMessageService: service wrong class");
	}
	
	public static void serve(long time, BasicMessage message) {
		GetChunk gc = new GetChunk(time, message);
		gc.start();
	}
}
