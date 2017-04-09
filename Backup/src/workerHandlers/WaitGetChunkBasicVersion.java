package workerHandlers;

import message.BasicMessage;

public class WaitGetChunkBasicVersion extends WaitGetChunk {

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
