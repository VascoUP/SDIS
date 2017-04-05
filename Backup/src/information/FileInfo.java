package information;

import file.HandleXMLFile;

import java.util.ArrayList;


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
			System.exit( 0);
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
	private static void eliminateSameBackedUpChunk(Chunk chunk) {
        for ( Chunk backedUpChunk : backedUpChunks ) {
            if( backedUpChunk.getChunkId() == chunk.getChunkId() &&
                backedUpChunk.getStorePath().equals(chunk.getStorePath()) ) {
                backedUpChunks.remove(backedUpChunk);
                return ;
            }
        }
	}
	
	private static void eliminateSameStoredChunk(Chunk chunk) {
		for ( Chunk storedChunk : storedChunks) {
			if( storedChunk.getChunkId() == chunk.getChunkId() &&
				storedChunk.getFileId().equals(chunk.getFileId()) ) {
				storedChunks.remove(storedChunk);
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


}
