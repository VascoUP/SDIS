package workerHandlers;

import message.BasicMessage;

/**
 * 
 * This class provides a handler that waits to get a chunk (this is a basic version)
 * This extends the WaitGetChunk class
 *
 */
public class WaitGetChunkBasicVersion extends WaitGetChunk {

	/**
	 * WaitGetChunkBasicVersion's constructor
	 * @param time Service's time
	 * @param message Basic message
	 */
	public WaitGetChunkBasicVersion(long time, BasicMessage message) {
		super(time, message);
	}
	
	/**
	 * Verifies if the MessageInfoGetChunk and the basic message created aren't null and if the hashmap size is less than 1
	 * @return true if the condition is verified, false otherwise
	 */
	@Override
	public boolean condition() {				
		return true;
	}
}
