package information;

import java.util.ArrayList;
import java.util.Iterator;

import file.HandleFile;
import file.HandleXMLFile;


public class FileInfo {
	private static ArrayList<Chunk> storedChunks;
	private static ArrayList<Chunk> backedUpChunks;	
	
	public static void addBackedUpChunk(Chunk chunk) {
		fileAddBackedUpChunk(chunk);
		backupChunk(chunk);
	}

	public static void addStoredChunk(Chunk chunk) {
		String path = chunk.getStorePath();
		if( HandleFile.isFile(path) )
			storeChunk(chunk);
		else
			fileElimStoredChunk(chunk);
	}
	
	public static void backupChunk(Chunk chunk) {
		backedUpChunks.add(chunk);
	}
	
	public static void eliminateBackedUpChunk(Chunk chunk) {
		try {
			HandleXMLFile.removeBackedUpChunk(chunk.getFileId(), "" + chunk.getChunkId(), chunk.getStorePath());
		} catch (Exception e) {
			
		}
	}
	
	public static void eliminateBackedUpFile(String path) {
		try {
			HandleXMLFile.removeBackedUpFile(path);
		} catch (Exception e) {
		}
	}
	
	public static void fileAddBackedUpChunk(Chunk chunk) {		
		try {
			HandleXMLFile.addBackedUpChunk(chunk);
		} catch (Exception e) {
			e.printStackTrace();
			return ;
		}
	}
	
	public static void fileAddStoredChunk(Chunk chunk) {
		try {
			HandleXMLFile.addStoreChunk(chunk);
		} catch (Exception e) {
			e.printStackTrace();
			return ;
		}
	}
	
	public static void fileElimStoredChunk(Chunk chunk) {
		try {
			HandleXMLFile.removeStoredChunk(chunk.getFileId(), "" + chunk.getChunkId());
		} catch (Exception e) {
			
		}
	}
	
	public static Chunk findBackedUpChunk(String path) {
		Iterator<Chunk> iter = backedUpChunks.iterator();
		while(iter.hasNext()) {
			Chunk c = iter.next();
			if(c.getStorePath().equals(path))
				return c;
		}
		return null;
	}
	
	public static Chunk findStoredChunk(Chunk chunk) {
		Iterator<Chunk> iter = storedChunks.iterator();
		while(iter.hasNext()) {
			Chunk c = iter.next();
			if(iter.equals(chunk))
				return c;
		}
		return null;
	}

	public static ArrayList<Chunk> getBackedUpChunks() {
		return backedUpChunks;
	}
	
	public static ArrayList<Chunk> getStoredChunks() {
		return storedChunks;
	}

	public static void init() {
		initStoredChunks();
		initBackedUpChunks();
		
		try {
			HandleXMLFile.readDocument();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static void initBackedUpChunks() {
		backedUpChunks = new ArrayList<Chunk>();
	}
	
	public static void initStoredChunks() {
		storedChunks = new ArrayList<Chunk>();
	}
	
	public static void removeBackedUpChunk(Chunk chunk) {
		backedUpChunks.remove(chunk);
		eliminateBackedUpChunk(chunk);
	}
	
	public static void removeStoredChunk(Chunk chunk) {
		storedChunks.remove(chunk);
		fileElimStoredChunk(chunk);
	}
	
	public static void storeChunk(Chunk chunk) {
		storedChunks.add(chunk);
	}
}
