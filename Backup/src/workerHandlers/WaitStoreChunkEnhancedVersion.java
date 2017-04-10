package workerHandlers;

import message.BasicMessage;
import spacemanaging.SpaceManager;

/**
 * 
 * This class provides a handler that waits to store a chunk (this is a enhanced version)
 * This extends the WaitStoreChunk class
 *
 */
public class WaitStoreChunkEnhancedVersion extends WaitStoreChunk {

	/**
	 * WaitStoreChunkEnhancedVersion's constructor
	 * @param time Service's time
	 * @param message Basic message
	 */
	public WaitStoreChunkEnhancedVersion(long time, BasicMessage message) {
		super(time, message);
	}
	
	/**
	 * Verifies a group of conditions that makes possible the service's execution
	 * @return true if the conditions is verified, false otherwise
	 */
	@Override
	public boolean condition() {
		getValue();
		System.out.println(prepdeg + " vs" + info.getReplicationDegree());
		return 	((	info != null && 
					prepdeg < info.getReplicationDegree()
				) ||
				(	hasStoredChunk()
				)) &&
				SpaceManager.instance.canStoreChunk(info.getChunk().length);
	}
}
