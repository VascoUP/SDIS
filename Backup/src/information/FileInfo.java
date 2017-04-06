package information;

import java.util.ArrayList;

import file.HandleFile;
import file.HandleXMLFile;


public class FileInfo {
	private static ArrayList<Chunk> storedChunks;
	private static ArrayList<Chunk> backedUpChunks;	

	
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
		backedUpChunks = new ArrayList<Chunk>();
	}
	
	private static void initStoredChunks() {
		storedChunks = new ArrayList<Chunk>();
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
	public static void backupChunk(Chunk chunk) {
		eliminateSameBackedUpChunk(chunk);
		backedUpChunks.add(chunk);
	}

	public static void storeChunk(Chunk chunk) {
		eliminateSameStoredChunk(chunk);
		storedChunks.add(chunk);
	}


	/*=================
	 * FILES FUNCTIONS
	 *=================
	 */
	private static void fileAddBackedUpChunk(Chunk chunk) {		
		try {
			HandleXMLFile.addBackedUpChunk(chunk);
		} catch (Exception e) {
			e.printStackTrace();
			return ;
		}
	}
	
	private static void fileAddStoredChunk(Chunk chunk) {
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
	public static void addBackedUpChunk(Chunk chunk) {
		fileAddBackedUpChunk(chunk);
		backupChunk(chunk);
	}

	public static void addStoredChunk(Chunk chunk) {
		fileAddStoredChunk(chunk);
		storeChunk(chunk);
	}

	
	/*======
	 * FIND
	 *======
	 */
	public static Chunk[] findAllBackedUpChunks(String path) {
		ArrayList<Chunk> chunks = new ArrayList<Chunk>();
		
		for( Chunk c : backedUpChunks ) {
			if( c.getStorePath().equals(path) )
				chunks.add(c);			
		}
		
		return chunks.toArray(new Chunk[chunks.size()]);
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
