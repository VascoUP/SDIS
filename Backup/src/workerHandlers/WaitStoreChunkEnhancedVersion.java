package workerHandlers;

import information.FileInfo;
import message.BasicMessage;
import spacemanaging.SpaceManager;

public class WaitStoreChunkEnhancedVersion extends WaitStoreChunk {

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
		
		System.out.println("WaitStoreChunk: capacity	 " + SpaceManager.instance.getCapacity());
		System.out.println("WaitStoreChunk: stored size 	" + FileInfo.getStoredSize());
		System.out.println("WaitStoreChunk: info size	 " + info.getChunk().length);
		return 	((	info != null && 
					prepdeg < info.getReplicationDegree()
				) ||
				(	hasStoredChunk()
				)) &&
				SpaceManager.instance.canStoreChunk(info.getChunk().length);
	}
}
