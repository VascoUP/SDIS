package spacemanaging;

import java.util.Set;

import information.ChunkStored;
import information.FileInfo;

/**
 * 
 * This class creates a remove chunk's service that is invoked when the we manage the space
 *
 */
public class RemoveChunks {
	/**
	 * Deletes the some stored chunks
	 * @param removableChunks Set of removable chunks
	 */
	public static void delete(Set<ChunkStored> removableChunks) {
		for(ChunkStored c: removableChunks)
			FileInfo.eliminateSameStoredChunk(c);
	}
}
