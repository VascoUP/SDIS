package information;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import file.HandleFile;
import file.HandleXMLFile;


public class FileInfo {
	private static ArrayList<ChunkStored> storedChunks;
	private static ArrayList<ChunkBackedUp> backedUpChunks;
	
	private static Lock lock = new ReentrantLock();

	public static ArrayList<ChunkStored> getStoredChunks() {
		return storedChunks;
	}
	
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
		lock.lock();
		try {
			for (Iterator<ChunkBackedUp> iterator = backedUpChunks.iterator(); iterator.hasNext(); ) {
			    Chunk c = iterator.next();
			    if (c.getStorePath().equals(path)) {
			        iterator.remove();
			    }
			}
			
	    	fileElimBackedUpFile(path);
		} finally {
			lock.unlock();
		}
	}
	
	/*public static void eliminateSameBackedUpChunk(Chunk chunk) {
		lock.lock();
		try {
			for (Iterator<ChunkBackedUp> iterator = backedUpChunks.iterator(); iterator.hasNext(); ) {
			    Chunk c = iterator.next();
			    if (c.getChunkId() == chunk.getChunkId() && 
			    	c.getStorePath().equals(chunk.getStorePath())) {
				    System.out.println("eliminateSameBackedUpChunk: " + c.getChunkId());
			    	fileElimBackedUpChunk(c);
			        iterator.remove();
			    }
			}
		} finally {
			lock.unlock();
		}
	}*/
	
	public static void eliminateStoredFile(String fileID) {
		lock.lock();
		try {
			for (Iterator<ChunkStored> iterator = storedChunks.iterator(); iterator.hasNext(); ) {
			    Chunk c = iterator.next();
			    if (c.getFileId().equals(fileID)) {
				    HandleFile.deleteFile(HandleFile.getFileName(c.getFileId(), c.getChunkId()));
			        iterator.remove();
		        }
			}
			
			fileElimStoredFile(fileID);
		} finally {
			lock.unlock();
		}
	}
	
	public static void eliminateSameStoredChunk(Chunk chunk) {
		lock.lock();
		try {
			for (Iterator<ChunkStored> iterator = storedChunks.iterator(); iterator.hasNext(); ) {
			    Chunk c = iterator.next();
			    if (c.getChunkId() == chunk.getChunkId() && 
			    	c.getStorePath().equals(chunk.getStorePath())) {
					fileElimStoredChunk(c);
			        iterator.remove();
			    }
			}
		} finally {
			lock.unlock();
		}
	}
	
	

	/*============
	 * ADD CHUNKS
	 *============
	 */
	public static void backupChunk(ChunkBackedUp chunk) {
		//eliminateSameBackedUpChunk(chunk);
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
			e.printStackTrace();
		}
	}
	
	private static void fileElimStoredChunk(Chunk chunk) {
		try {
			HandleXMLFile.removeStoredChunk(chunk.getFileId(), "" + chunk.getChunkId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		HandleFile.deleteFile(HandleFile.getFileName(chunk.getFileId(), chunk.getChunkId()));
	}
	
	private static void fileElimStoredFile(String fileID) {
		try {
			HandleXMLFile.removeStoredFile(fileID);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		
		lock.lock();
		try {
			for( ChunkBackedUp c : backedUpChunks ) {
				if( c.getStorePath().equals(path) )
					chunks.add(c);			
			}
		} finally {
			lock.unlock();
		}
		
		return chunks.toArray(new ChunkBackedUp[chunks.size()]);
	}
	
	public static Chunk[] findAllStoredChunks(String fileID) {
		ArrayList<Chunk> chunks = new ArrayList<Chunk>();

		lock.lock();
		try {
			for( Chunk c : storedChunks ) {
				if( c.getFileId().equals(fileID) )
					chunks.add(c);			
			}
		} finally {
			lock.unlock();
		}
		
		return chunks.toArray(new Chunk[chunks.size()]);
	}
	
	public static Chunk findStoredChunk(String fileID, int chunkID) {
		Chunk chunk = null;
		lock.lock();
		try {
			for( Chunk c : storedChunks ) {
				if( c.getFileId().equals(fileID) ) {
					chunk = c;
					break;
				}
			}
		} finally {
			lock.unlock();
		}
		
		return chunk;
	}
	
	public static int getStoredSize() {
		int stored = 0;
		for( ChunkStored chunk : storedChunks )
			stored += chunk.getSize();
		return stored;
	}
	
	public String toString(){
		String message = new String();
		
		lock.lock();
		try {
		
		message += "\nStored Chunks\n";
		for(int i=0; i<storedChunks.size(); i++)
			message += "Chunk ID: " + storedChunks.get(i).chunkId + " Size: " + storedChunks.get(i).getSize() 
						+ " Perceived Replication Degree: " + storedChunks.get(i).getPRepDeg() + "\n";		
		
		message += "\nBackuped Chunks\n";
		for(int j=0; j<backedUpChunks.size(); j++)
			message += "File pathname: " + backedUpChunks.get(j).getStorePath() + " Backup ID: " + backedUpChunks.get(j).getServiceID()
						+ " Desired Replication Degree: " + backedUpChunks.get(j).getDRepDeg() + " Chunk ID: " + backedUpChunks.get(j).getChunkId() 
						+ " Chunk Perceived Replication Degree: " + backedUpChunks.get(j).getPRepDeg() + "\n";
		
		} finally {
			lock.unlock();
		}
		
		return message;
	}
}
