package information;

import java.util.ArrayList;
import java.util.Iterator;

import file.HandleFile;
import file.HandleXMLFile;


public class FileInfo {
	private static ArrayList<ChunkStored> storedChunks;
	private static ArrayList<ChunkBackedUp> backedUpChunks;	

	
	/*======
	 * INIT
	 *======
	 */
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
	
	private static void initBackedUpChunks() {
		backedUpChunks = new ArrayList<>();
	}
	
	private static void initStoredChunks() {
		storedChunks = new ArrayList<>();
	}
	
	
	/*===========
	 * ELIMINATE
	 *===========
	 */
	public static void eliminateBackedUpFile(String path) {
		for (Iterator<ChunkBackedUp> iterator = backedUpChunks.iterator(); iterator.hasNext(); ) {
		    Chunk c = iterator.next();
		    System.out.println(c.getStorePath() + " vs " + path);
		    if (c.getStorePath().equals(path)) {
			    System.out.println("Remove");
		        iterator.remove();
	        }
		}
		
    	fileElimBackedUpFile(path);
	}
	
	public static void eliminateSameBackedUpChunk(Chunk chunk) {
		for (Iterator<ChunkBackedUp> iterator = backedUpChunks.iterator(); iterator.hasNext(); ) {
		    Chunk c = iterator.next();
		    if (c.getChunkId() == chunk.getChunkId() && c.getStorePath().equals(chunk.getStorePath())) {
		    	fileElimBackedUpChunk(c);
		        iterator.remove();
		    }
		}
	}
	
	public static void eliminateSameStoredChunk(Chunk chunk) {		
		for (Iterator<ChunkStored> iterator = storedChunks.iterator(); iterator.hasNext(); ) {
		    Chunk c = iterator.next();
		    if (c.getChunkId() == chunk.getChunkId() && c.getStorePath().equals(chunk.getStorePath())) {
				fileElimStoredChunk(c);
		        iterator.remove();
		    }
		}
	}
	

	/*============
	 * ADD CHUNKS
	 *============
	 */
	public static void backupChunk(ChunkBackedUp chunk) {
		eliminateSameBackedUpChunk(chunk);
		backedUpChunks.add(chunk);
	}

	public static void storeChunk(ChunkStored chunk) {
		eliminateSameStoredChunk(chunk);
		storedChunks.add(chunk);
	}


	/*=================
	 * FILES FUNCTIONS
	 *=================
	 */
	private static void fileAddBackedUpChunk(ChunkBackedUp chunk) {		
		try {
			HandleXMLFile.addBackedUpChunk(chunk);
		} catch (Exception e) {
			e.printStackTrace();
			return ;
		}
	}
	
	private static void fileAddStoredChunk(ChunkStored chunk) {
		try {
			HandleXMLFile.addStoreChunk(chunk);
		} catch (Exception e) {
			e.printStackTrace();
			return ;
		}
	}
	
	private static void fileElimBackedUpFile(String path) {
		try {
			HandleXMLFile.removeBackedUpFile(path);
		} catch (Exception e) {
		}
	}
	
	private static void fileElimBackedUpChunk(Chunk chunk) {
		try {
			HandleXMLFile.removeBackedUpChunk(chunk.getFileId(), "" + chunk.getChunkId(), chunk.getStorePath());
		} catch (Exception e) {
		}
	}
	
	private static void fileElimStoredChunk(Chunk chunk) {
		try {
			HandleXMLFile.removeStoredChunk(chunk.getFileId(), "" + chunk.getChunkId());
		} catch (Exception e) {
		}
		
		HandleFile.deleteFile(HandleFile.getFileName(chunk.getFileId(), chunk.getChunkId()));
	}

	
	/*==============
	 * FILE AND ADD
	 *==============
	 */
	public static void addBackedUpChunk(ChunkBackedUp chunk) {
		backupChunk(chunk);
		fileAddBackedUpChunk(chunk);
	}

	public static void addStoredChunk(ChunkStored chunk) {
		storeChunk(chunk);
		fileAddStoredChunk(chunk);
	}

	
	/*======
	 * FIND
	 *======
	 */
	public static ChunkBackedUp[] findAllBackedUpChunks(String path) {
		ArrayList<Chunk> chunks = new ArrayList<Chunk>();
		
		for( ChunkBackedUp c : backedUpChunks ) {
			if( c.getStorePath().equals(path) )
				chunks.add(c);			
		}
		
		return chunks.toArray(new ChunkBackedUp[chunks.size()]);
	}
	
	public static Chunk[] findAllStoredChunks(String fileID) {
		ArrayList<Chunk> chunks = new ArrayList<Chunk>();
		
		for( Chunk c : storedChunks ) {
			if( c.getFileId().equals(fileID) )
				chunks.add(c);			
		}
		
		return chunks.toArray(new Chunk[chunks.size()]);
	}
}
