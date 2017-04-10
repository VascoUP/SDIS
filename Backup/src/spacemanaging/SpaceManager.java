package spacemanaging;

import java.util.Set;

import file.HandleXMLFile;
import information.ChunkStored;
import information.FileInfo;
import protocol.Remove;

/**
 * 
 * This class creates a space's manager
 *
 */
public class SpaceManager {
	public static SpaceManager instance;			//Space manager's instance
	private int diskCapacity = Integer.MAX_VALUE;	//Disk maximum capacity
	
	/**
	 * SpaceManager's constructor 
	 */
	private SpaceManager() {
		this.diskCapacity = Integer.MAX_VALUE;
	}
	
	/**
	 * Initializes the space's manager
	 * @return The space manager's initialized
	 * @throws Exception This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	public static SpaceManager initializeManager() throws Exception {
		if( instance != null )
			throw new Exception("Trying to initialize a singleton variable for the second time");
		instance = new SpaceManager();
		return instance;
	}
	
	/**
	 * Gets the disk's capacity
	 * @return The disk's capacity
	 */
	public int getCapacity() {
		return diskCapacity;
	}
	
	/**
	 * Sets the disk's capacity
	 * @param nCapacity New disk's capacity
	 */
	public void setCapacity(int nCapacity) {
		try {
			HandleXMLFile.updateDiskSpace(nCapacity);
		} catch (Exception ignore) {
		}
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

	/**
	 * Verifies if it's possible to store a chunk
	 * @param size Chunk's size
	 * @return true if the disk's capacity is less than the size plus the stored's size, false otherwise
	 */
	public boolean canStoreChunk(int size) {
		return diskCapacity >= size + FileInfo.getStoredSize();			
	}
}
