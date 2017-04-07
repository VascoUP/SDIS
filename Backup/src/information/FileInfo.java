package information;

import java.util.ArrayList;

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
	public static void eliminateSameBackedUpChunk(Chunk chunk) {
		for( Chunk c : backedUpChunks ) {
			if( c.getChunkId() == chunk.getChunkId() && c.getStorePath().equals(chunk.getStorePath()) ) {
				fileElimBackedUpChunk(c);
				backedUpChunks.remove(c);
				HandleFile.deleteFile(HandleFile.getFileName(c.getFileId(), c.getChunkId()));
				return ;
			}			
		}
	}
	
	public static void eliminateSameStoredChunk(Chunk chunk) {
		for( Chunk c : storedChunks ) {
			if( c.getChunkId() == chunk.getChunkId() && c.getFileId().equals(chunk.getFileId()) ) {
				fileElimStoredChunk(c);
				storedChunks.remove(c);
				return ;
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
	
	private static void fileElimBackedUpChunk(Chunk chunk) {
		try {
			HandleXMLFile.removeBackedUpFile(HandleFile.getFileName(chunk.getFileId(), chunk.getChunkId()));
		} catch (Exception e) {
		}
	}
	
	private static void fileElimStoredChunk(Chunk chunk) {
		try {
			HandleXMLFile.removeStoredChunk(chunk.getFileId(), "" + chunk.getChunkId());
		} catch (Exception e) {
		}
	}

	
	/*==============
	 * FILE AND ADD
	 *==============
	 */
	public static void addBackedUpChunk(ChunkBackedUp chunk) {
		fileAddBackedUpChunk(chunk);
		backupChunk(chunk);
	}

	public static void addStoredChunk(ChunkStored chunk) {
		fileAddStoredChunk(chunk);
		storeChunk(chunk);
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
