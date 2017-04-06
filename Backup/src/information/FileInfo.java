package information;

import java.util.ArrayList;
import java.util.Iterator;

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
	private static void eliminateSameBackedUpChunk(Chunk chunk) {
		Iterator<Chunk> iter = backedUpChunks.iterator();
		while( iter.hasNext() ) {
			Chunk c = iter.next();
			if( c.getChunkId() == chunk.getChunkId() && c.getStorePath().equals(chunk.getStorePath()) ) {
				backedUpChunks.remove(c);
				return ;
			}
		}
	}
	
	private static void eliminateSameStoredChunk(Chunk chunk) {
		Iterator<Chunk> iter = storedChunks.iterator();
		while( iter.hasNext() ) {
			Chunk c = iter.next();
			if( c.getChunkId() == chunk.getChunkId() && c.getFileId().equals(chunk.getFileId()) ) {
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
	
	/*private static void fileElimStoredChunk(Chunk chunk) {
		try {
			HandleXMLFile.removeStoredChunk(chunk.getFileId(), "" + chunk.getChunkId());
		} catch (Exception e) {
		}
	}*/

	
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
		Iterator<Chunk> iter = backedUpChunks.iterator();
		
		while( iter.hasNext() ) {
			Chunk c = iter.next();
			if( c.getStorePath().equals(path) )
				chunks.add(c);
		}
		
		return chunks.toArray(new Chunk[chunks.size()]);
	}
}
