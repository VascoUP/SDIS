package spacemanaging;

import java.util.Set;

import information.ChunkStored;
import information.FileInfo;
import protocol.Remove;

public class SpaceManager {
	public static SpaceManager instance;
	private int diskCapacity = Integer.MAX_VALUE;
	
	private SpaceManager() {
		this.diskCapacity = Integer.MAX_VALUE;
	}
	
	public static SpaceManager initializeManager() throws Exception {
		if( instance != null )
			throw new Exception("Trying to initialize a singleton variable for the second time");
		instance = new SpaceManager();
		return instance;
	}
	
	public int getCapacity() {
		return diskCapacity;
	}
	
	public void setCapacity(int nCapacity) {
		int currCapacity = diskCapacity;
		diskCapacity = nCapacity;
		if( currCapacity <= nCapacity ) {
			System.out.println("SpaceManager: " + currCapacity + " - " + nCapacity + " no need to remove chunks");
			return ;
		}
		Set<ChunkStored> removableChunks = ProcessChunks.bestRemovableChunks();
		Remove remove = new Remove(removableChunks);
		remove.run_service();
		RemoveChunks.delete(removableChunks);
	}

	public boolean canStoreChunk(int size) {
		return diskCapacity < size + FileInfo.getStoredSize();			
	}
}
