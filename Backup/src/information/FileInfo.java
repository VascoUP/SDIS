package information;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import file.HandleFile;
import file.HandleXMLFile;

/**
 * 
 * This class builds the file's information
 *
 */
public class FileInfo {
	private static ArrayList<ChunkStored> storedChunks;			//ArrayList with the stored chunks
	private static ArrayList<ChunkBackedUp> backedUpChunks;		//ArrayList with the backed up chunks
	
	private static Lock lock = new ReentrantLock(); 			//Creates an instance of ReentrantLock

	/**
	 * Gets the ArrayList with the stored chunks
	 * @return The ArrayList with the stored chunks
	 */
	public static ArrayList<ChunkStored> getStoredChunks() {
		return storedChunks;
	}
	
	/*
	 *======
	 * INIT
	 *======
	 */
	
	/**
	 * Initiates the stored chunks and the backed up chunks, reading the XML file
	 */
	public static void init() {
		initStoredChunks(); 	//Initiates the stores chunks
		initBackedUpChunks();	//Initiates the backed up chunks
		
		try {
			HandleXMLFile.readDocument();	//Reads the XML file
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * Initiates the backed up chunks
	 */
	private static void initBackedUpChunks() {
		backedUpChunks = new ArrayList<>();
	}
	
	/**
	 * Initiates the stored chunks
	 */
	private static void initStoredChunks() {
		storedChunks = new ArrayList<>();
	}
		
	
	/*
	 *===========
	 * ELIMINATE
	 *===========
	 */
	
	/**
	 * Eliminates the backed up chunks from XML file
	 * @param path File's path
	 */
	public static void eliminateBackedUpFile(String path) {
		lock.lock(); //Acquires the lock
		try {
			for (Iterator<ChunkBackedUp> iterator = backedUpChunks.iterator(); iterator.hasNext(); ) {
			    Chunk c = iterator.next();
			    if (c.getStorePath().equals(path)) {
			        iterator.remove();
			    }
			}
			
	    	fileElimBackedUpFile(path);
		} finally {
			lock.unlock(); //Releases the lock
		}
	}
	
	/**
	 * Eliminates the same backed up chunk
	 * @param chunk Chunk that will be compared
	 */
	public static void eliminateSameBackedUpChunk(Chunk chunk) {
		lock.lock();
		try {
			for (Iterator<ChunkBackedUp> iterator = backedUpChunks.iterator(); iterator.hasNext(); ) {
				ChunkBackedUp c = iterator.next();
			    if (c.getChunkId() == chunk.getChunkId() && 
			    	c.getFileId().equals(chunk.getFileId())) {
			    	fileElimBackedUpChunk(c);
			        iterator.remove();
			    }
			}
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * Eliminates the stored chunks from XML file
	 * @param fileID File's ID
	 */
	public static void eliminateStoredFile(String fileID) {
		lock.lock(); //Acquires the lock
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
			lock.unlock(); //Releases the lock
		}
	}
	
	/**
	 * Eliminates the same stored chunk
	 * @param chunk Chunk that will be compared
	 */
	public static void eliminateSameStoredChunk(Chunk chunk) {
		lock.lock(); //Acquires the lock
		try {
			for (Iterator<ChunkStored> iterator = storedChunks.iterator(); iterator.hasNext(); ) {
				ChunkStored c = iterator.next();
			    if (c.getChunkId() == chunk.getChunkId() && 
			    	c.getFileId().equals(chunk.getFileId())) {
					fileElimStoredChunk(c);
			        iterator.remove();
			    }
			}
		} finally {
			lock.unlock(); //Releases the lock
		}
	}
	
	

	/*============
	 * ADD CHUNKS
	 *============
	 */
	
	/**
	 * Adds a backed up chunk to the respective ArrayList
	 * @param chunk Backed up chunk that will be added
	 */
	public static void backupChunk(ChunkBackedUp chunk) {
		eliminateSameBackedUpChunk(chunk);
		backedUpChunks.add(chunk);
		System.out.println("FileInfo: backup chunk added");
	}

	/**
	 * Adds a stored chunk to the respective ArrayList
	 * @param chunk Stored chunk that will be added
	 */
	public static void storeChunk(ChunkStored chunk) {
		eliminateSameStoredChunk(chunk);
		storedChunks.add(chunk);
	}


	/*=================
	 * FILES FUNCTIONS
	 *=================
	 */
	
	/**
	 * Adds a backed up chunk to the XML file
	 * @param chunk Backed up chunk that will be added
	 */
	private static void fileAddBackedUpChunk(ChunkBackedUp chunk) {
		try {
			HandleXMLFile.addBackedUpChunk(chunk);
		} catch (Exception e) {
			e.printStackTrace();
			return ;
		}
	}
	
	/**
	 * Adds a stored chunk to the XML file
	 * @param chunk Stored chunk that will be added
	 */
	private static void fileAddStoredChunk(ChunkStored chunk) {
		try {
			HandleXMLFile.addStoreChunk(chunk);
		} catch (Exception e) {
			e.printStackTrace();
			return ;
		}
	}

	/**
	 * Removes a stored chunk from a file
	 * @param chunk Chunk to be removed
	 */
	private static void fileElimBackedUpChunk(Chunk chunk) {
		try {
			HandleXMLFile.removeBackedUpChunk(chunk.getFileId(), "" + chunk.getChunkId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		HandleFile.deleteFile(HandleFile.getFileName(chunk.getFileId(), chunk.getChunkId())); //Deletes the chunk's file
	}
	
	/**
	 * Removes a backed up chunk from a file
	 * @param path File's path name
	 */
	private static void fileElimBackedUpFile(String path) {
		try {
			HandleXMLFile.removeBackedUpFile(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes a stored chunk from a file
	 * @param chunk Chunk to be removed
	 */
	private static void fileElimStoredChunk(Chunk chunk) {
		try {
			HandleXMLFile.removeStoredChunk(chunk.getFileId(), "" + chunk.getChunkId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		HandleFile.deleteFile(HandleFile.getFileName(chunk.getFileId(), chunk.getChunkId())); //Deletes the chunk's file
	}
	
	/**
	 * Removes the stored files
	 * @param fileID File's ID
	 */
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
	
	/**
	 * Adds a backed up chunk to the XML file
	 * @param chunk Backed up chunk that will be added
	 */
	public static void addBackedUpChunk(ChunkBackedUp chunk) {
		backupChunk(chunk);			//Adds the backed up chunk to the ArrayList
		fileAddBackedUpChunk(chunk);
	}

	/**
	 * Adds a stored chunk to the XML file
	 * @param chunk Stored chunk that will be added
	 */
	public static void addStoredChunk(ChunkStored chunk) {
		storeChunk(chunk);			//Adds the stored chunk to the ArrayList
		fileAddStoredChunk(chunk);
	}

	
	/*======
	 * FIND
	 *======
	 */
	
	/**
	 * Finds all the backed up chunks
	 * @param path File's path
	 * @return A array with all the backed up chunks
	 */
	public static ChunkBackedUp[] findAllBackedUpChunks(String path) {
		ArrayList<Chunk> chunks = new ArrayList<Chunk>();
		
		lock.lock(); //Acquires the lock
		try {
			for( ChunkBackedUp c : backedUpChunks ) {
				if( c.getStorePath().equals(path) )
					chunks.add(c);			
			}
		} finally {
			lock.unlock(); //Releases the lock
		}
		
		return chunks.toArray(new ChunkBackedUp[chunks.size()]);
	}
	
	/**
	 * Finds all the backed up chunks
	 * @param path File's path
	 * @return A array with all the backed up chunks
	 */
	public static ChunkBackedUp findBackedUpChunk(String fileID, int chunkID) {
		ChunkBackedUp chunk = null;
		
		lock.lock(); //Acquires the lock
		try {
			for( ChunkBackedUp c : backedUpChunks ) {
				System.out.println(c.getFileId() + " vs " + fileID);
				System.out.println(c.getChunkId() + " vs " + chunkID);
				if( c.getChunkId() == chunkID &&
					c.getFileId().equals(fileID) ) {
					chunk = c;
					break;
				}
			}
		} finally {
			lock.unlock(); //Releases the lock
		}
		
		return chunk;
	}
	
	/**
	 * Finds all the stored chunks using the file's ID
	 * @param fileID File's ID
	 * @return A array with all the stored chunks
	 */
	public static Chunk[] findAllStoredChunks(String fileID) {
		ArrayList<Chunk> chunks = new ArrayList<Chunk>();

		lock.lock(); //Acquires the lock
		try {
			for( Chunk c : storedChunks ) {
				if( c.getFileId().equals(fileID) )
					chunks.add(c);			
			}
		} finally {
			lock.unlock(); //Releases the lock
		}
		
		return chunks.toArray(new Chunk[chunks.size()]);
	}
	
	/**
	 * Finds a stored chunks using the file's ID and the chunk's ID
	 * @param fileID File's ID
	 * @param chunkID Chunk's ID
	 * @return The stored chunk found
	 */
	public static ChunkStored findStoredChunk(String fileID, int chunkID) {
		ChunkStored chunk = null;
		lock.lock(); //Acquires the lock
		try {
			for( ChunkStored c : storedChunks ) {
				if( c.getFileId().equals(fileID) ) {
					chunk = c;
					break;
				}
			}
		} finally {
			lock.unlock(); //Releases the lock
		}
		
		return chunk;
	}
	
	
	/**
	 * Gets the total stored chunks' size
	 * @return The total stored chunks' size
	 */
	public static int getStoredSize() {
		int stored = 0;
		for( ChunkStored chunk : storedChunks )
			stored += chunk.getSize();
		return stored;
	}
	
	/**
	 * Gets the information saved into the backed up chunks and the stored chunks
	 * @return The information saved into the backed up chunks and the stored chunks
	 */
	public static String getString(){
		StringBuilder message = new StringBuilder();
		
		lock.lock(); //Acquires the lock
		try {
			message.append("\nStored size: ");
			message.append(getStoredSize());
			message.append("\n");
			
			message.append("\nStored Chunks\n");
			for(int i=0; i<storedChunks.size(); i++) {
				message.append("Chunk ID: ");
				message.append(storedChunks.get(i).chunkId);
				message.append("\n");
				message.append("- Size: ");
				message.append(storedChunks.get(i).getSize());
				message.append("\n");
				message.append("- Perceived Replication Degree: ");
				message.append(storedChunks.get(i).getPRepDeg());
				message.append("\n");
			}
			
			message.append("\nBackuped Chunks\n");
			for(int j=0; j<backedUpChunks.size(); j++) {
				message.append("File pathname: ");
				message.append(backedUpChunks.get(j).getStorePath());
				message.append("\n");
				message.append("- Backup ID: ");
				message.append(backedUpChunks.get(j).getServiceID());
				message.append("\n");
				message.append("- Desired Replication Degree: ");
				message.append(backedUpChunks.get(j).getDRepDeg());
				message.append("\n");
				message.append("- Chunk ID: ");
				message.append(backedUpChunks.get(j).getChunkId());
				message.append("\n");
				message.append("- Chunk Perceived Replication Degree: ");
				message.append(backedUpChunks.get(j).getPRepDeg());
				message.append("\n");
			}
		} finally {
			lock.unlock(); //Releases the lock
		}
		
		return new String(message);
	}
}
