package spacemanaging;

import java.util.Set;

import information.Chunk;
import information.FileInfo;

public class RemoveChunks {
	public static void delete(Set<Chunk> removableChunks) {
		for(Chunk c: removableChunks)
			FileInfo.eliminateSameStoredChunk(c);
	}
}
