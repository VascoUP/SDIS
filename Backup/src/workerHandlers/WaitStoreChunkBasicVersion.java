package workerHandlers;

import message.BasicMessage;
import spacemanaging.SpaceManager;

/**
 * 
 * This class provides a handler that waits to store a chunk (this is a basic version)
 * This extends the WaitStoreChunk class
 *
 */
public class WaitStoreChunkBasicVersion extends WaitStoreChunk {

	/**
	 * WaitStoreChunkBasicVersion's constructor
	 * @param time Service's time
	 * @param message Basic message
	 */
	public WaitStoreChunkBasicVersion(long time, BasicMessage message) {
		super(time, message);
	}
	
	/**
	 * Verifies a group of conditions that makes possible the service's execution
	 * @return true if the conditions is verified, false otherwise
	 */
	@Override
	public boolean condition() {
		return SpaceManager.instance.canStoreChunk(info.getChunk().length);
	}

}
