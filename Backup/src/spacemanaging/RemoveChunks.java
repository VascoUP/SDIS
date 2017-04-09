package spacemanaging;

import java.util.Set;

import information.ChunkStored;
import information.FileInfo;

public class RemoveChunks {
	public static void delete(Set<ChunkStored> removableChunks) {
		for(ChunkStored c: removableChunks)
			FileInfo.eliminateSameStoredChunk(c);
	}
}
