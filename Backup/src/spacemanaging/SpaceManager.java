package spacemanaging;

import java.util.Set;

import information.Chunk;

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
		if( currCapacity <= nCapacity )
			return ;
		Set<Chunk> removableChunks = ProcessChunks.bestRemovableChunks();
		RemoveChunks.delete(removableChunks);
	}
}
