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
import information.Chunk;
import information.ChunkStored;
import information.FileInfo;
import message.MessageConst;

public class ProcessChunks {
	public static Set<Chunk> bestRemovableChunks() {
		HashSet<Chunk> removableChunks = new HashSet<Chunk>();
		
		if( SpaceManager.instance.getCapacity() <= FileInfo.getStoredSize() )
			return removableChunks;
		
		TreeMap<Double, ArrayList<ChunkStored>> classifications = classifyChunks();
		removableChunks = removableChunks(classifications);

		return removableChunks;
	}
	
	private static HashSet<Chunk> removableChunks(TreeMap<Double, ArrayList<ChunkStored>> classifications) {
		HashSet<Chunk> removableChunks = new HashSet<Chunk>();
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
	
	private static double classifyChunk(ChunkStored chunk) {
		String pathName = HandleFile.getFileName(chunk.getFileId(), chunk.getChunkId());
		File file = new File(pathName);
		double percentageTime = 100 - (double)(100 * file.lastModified() / Instant.now().toEpochMilli());
		double percentageSize = 100 * chunk.getSize() / MessageConst.CHUNKSIZE;
		double percentageRemainingSpace = 
				 100 * (FileInfo.getStoredSize() - chunk.getSize()) / SpaceManager.instance.getCapacity();
		return percentageTime + 4 * percentageRemainingSpace + 0.2 * percentageSize;
	}
}
