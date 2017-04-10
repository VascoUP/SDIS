package spacemanaging;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import file.HandleFile;
import information.ChunkStored;
import information.FileInfo;
import message.MessageConst;

/**
 * 
 * This class processes the different chunks
 *
 */
public class ProcessChunks {
	/**
	 * Gets the best removable chunks
	 * @return A set of chunks that represents the best removable chunks
	 */
	public static Set<ChunkStored> bestRemovableChunks() {
		HashSet<ChunkStored> removableChunks = new HashSet<>();
		
		if( SpaceManager.instance.getCapacity() >= FileInfo.getStoredSize() )
			return removableChunks;
		
		TreeMap<Double, ArrayList<ChunkStored>> classifications = classifyChunks();
		removableChunks = removableChunks(classifications);

		return removableChunks;
	}
	
	/**
	 * Gets the removable chunks
	 * @param classifications Chunks' classifications
	 * @return A HashSet with the removable chunks
	 */
	private static HashSet<ChunkStored> removableChunks(TreeMap<Double, ArrayList<ChunkStored>> classifications) {
		HashSet<ChunkStored> removableChunks = new HashSet<>();
		int currStoredCapacity = FileInfo.getStoredSize();
		
		for(Map.Entry<Double,ArrayList<ChunkStored>> entry : classifications.entrySet()) {
			ArrayList<ChunkStored> value = entry.getValue();
			for( ChunkStored v : value ) {
				currStoredCapacity -= v.getSize();
				removableChunks.add(v);
				
				if( SpaceManager.instance.getCapacity() >= currStoredCapacity )
					return removableChunks;
			}
		}
		
		return removableChunks;
	}

	/**
	 * Gets the chunks' classification
	 * @return A TreeMap with the chunk's classification
	 */
	private static TreeMap<Double, ArrayList<ChunkStored>> classifyChunks() {
		ArrayList<ChunkStored> storedChunks = FileInfo.getStoredChunks();
		TreeMap<Double, ArrayList<ChunkStored>> classifications = new TreeMap<>(Collections.reverseOrder());

		for( ChunkStored chunk : storedChunks ) {
			ArrayList<ChunkStored> chunks;
			double classification = classifyChunk(chunk);
			
			if( (chunks = classifications.get(classification)) == null ) {
				chunks = new ArrayList<>();
				chunks.add(chunk);
				classifications.put(classification, chunks);
			} else
				chunks.add(chunk);
		}
		
		return classifications;
	}
	
	/**
	 * Gets the chunk's individual classification
	 * @param chunk Chunk that we want to know the classification
	 * @return The chunk's individual classification
	 */
	private static double classifyChunk(ChunkStored chunk) {
		String pathName = HandleFile.getFileName(chunk.getFileId(), chunk.getChunkId());
		File file = new File(pathName);
		double percentageTime = 100 - (double)(100 * file.lastModified() / Instant.now().toEpochMilli());
		double percentageSize = 100 * chunk.getSize() / MessageConst.CHUNKSIZE;
		double percentageRemainingSpace = 
				 100 * (FileInfo.getStoredSize() - chunk.getSize()) / SpaceManager.instance.getCapacity();
		return percentageTime - 4 * percentageRemainingSpace + 400  + 0.2 * percentageSize;
	}
}
