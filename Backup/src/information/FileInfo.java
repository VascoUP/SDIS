package information;

import java.util.ArrayList;
import java.util.Iterator;

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
		 synchronized(FileInfo.class) {
			for (Iterator<ChunkBackedUp> iterator = backedUpChunks.iterator(); iterator.hasNext(); ) {
			    Chunk c = iterator.next();
			    if (c.getStorePath().equals(path)) {
			        iterator.remove();
			    }
			}
			
	    	fileElimBackedUpFile(path);
		}
	}
	
	/**
	 * Eliminates the same backed up chunk
	 * @param chunk Chunk that will be compared
	 */
	public static void eliminateSameBackedUpChunk(Chunk chunk) {
		synchronized(FileInfo.class) {
			for (Iterator<ChunkBackedUp> iterator = backedUpChunks.iterator(); iterator.hasNext(); ) {
				ChunkBackedUp c = iterator.next();
			    if (c.getChunkId() == chunk.getChunkId() && 
			    	c.getFileId().equals(chunk.getFileId())) {
			    	fileElimBackedUpChunk(c);
			        iterator.remove();
			    }
			}
		}
	}
	
	/**
	 * Eliminates the stored chunks from XML file
	 * @param fileID File's ID
	 */
	public static void eliminateStoredFile(String fileID) {
		 synchronized(FileInfo.class) {
			for (Iterator<ChunkStored> iterator = storedChunks.iterator(); iterator.hasNext(); ) {
			    Chunk c = iterator.next();
			    if (c.getFileId().equals(fileID)) {
				    HandleFile.deleteFile(HandleFile.getFileName(c.getFileId(), c.getChunkId()));
			        iterator.remove();
		        }
			}
			
			fileElimStoredFile(fileID);
		}
	}
	
	/**
	 * Eliminates the same stored chunk
	 * @param chunk Chunk that will be compared
	 */
	public static void eliminateSameStoredChunk(Chunk chunk) {
		 synchronized(FileInfo.class) {
			for (Iterator<ChunkStored> iterator = storedChunks.iterator(); iterator.hasNext(); ) {
				ChunkStored c = iterator.next();
			    if (c.getChunkId() == chunk.getChunkId() && 
			    	c.getFileId().equals(chunk.getFileId())) {
					fileElimStoredChunk(c);
			        iterator.remove();
			    }
			}
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
	}

	/**
	 * Adds a stored chunk to the respective ArrayList
	 * @param chunk Stored chunk that will be added
	 */
	public static void storeChunk(ChunkStored chunk) {
		eliminateSameStoredChunk(chunk);
		storedChunks.add(chunk);
	}

	/*===============
	 * UPDATE CHUNKS
	 *===============
	 */
	/**
	 * Updates an existing chunk with new values
	 * @param chunk update chunk with new values
	 */
	public static void updateStoredChunk(ChunkStored chunk) {
		 synchronized(FileInfo.class) {
			for (Iterator<ChunkStored> iterator = storedChunks.iterator(); iterator.hasNext(); ) {
				ChunkStored c = iterator.next();
			    if (c.getChunkId() == chunk.getChunkId() && 
			    	c.getFileId().equals(chunk.getFileId()))
			        iterator.remove();
			}
			storedChunks.add(chunk);
		}
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
		synchronized(FileInfo.class) {
			ArrayList<Chunk> chunks = new ArrayList<Chunk>();
			for( ChunkBackedUp c : backedUpChunks ) {
				System.out.println(path + " vs " + c.getStorePath());
				if( c.getStorePath().equals(path) )
					chunks.add(c);			
			}
			return chunks.toArray(new ChunkBackedUp[chunks.size()]);
		}
	}
	
	/**
	 * Finds all the backed up chunks
	 * @param path File's path
	 * @return A array with all the backed up chunks
	 */
	public static ChunkBackedUp findBackedUpChunk(String fileID, int chunkID) {
		synchronized(FileInfo.class) {
			ChunkBackedUp chunk = null;
			for( ChunkBackedUp c : backedUpChunks ) {
				if( c.getChunkId() == chunkID &&
					c.getFileId().equals(fileID) ) {
					chunk = c;
					break;
				}
			}
			return chunk;
		}
	}
	
	/**
	 * Finds all the stored chunks using the file's ID
	 * @param fileID File's ID
	 * @return A array with all the stored chunks
	 */
	public static Chunk[] findAllStoredChunks(String fileID) {
		synchronized(FileInfo.class) {
			ArrayList<Chunk> chunks = new ArrayList<Chunk>();
			for( Chunk c : storedChunks ) {
				if( c.getFileId().equals(fileID) )
					chunks.add(c);			
			}
			return chunks.toArray(new Chunk[chunks.size()]);
		}
	}
	
	/**
	 * Finds a stored chunks using the file's ID and the chunk's ID
	 * @param fileID File's ID
	 * @param chunkID Chunk's ID
	 * @return The stored chunk found
	 */
	public static ChunkStored findStoredChunk(String fileID, int chunkID) {
		synchronized(FileInfo.class) {
			ChunkStored chunk = null;
			for( ChunkStored c : storedChunks ) {
				if( c.getChunkId() == chunkID && c.getFileId().equals(fileID) ) {
					chunk = c;
					break;
				}
			}
			return chunk;
		}
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
		synchronized(FileInfo.class) {
			StringBuilder message = new StringBuilder();

			message.append("\nStored size: ");
			message.append(getStoredSize());
			message.append("\n");
			
			if(storedChunks.size() != 0){
				message.append("\nStored Chunks\n");
				for(int i=0; i<storedChunks.size(); i++) {
					message.append("File ID: ");
					message.append(storedChunks.get(i).getFileId());
					message.append("\n");
					message.append("- Chunk ID: ");
					message.append(storedChunks.get(i).getChunkId());
					message.append("\n");
					message.append("- Size: ");
					message.append(storedChunks.get(i).getSize());
					message.append("\n");
					message.append("- Desired Replication Degree: ");
					message.append(storedChunks.get(i).getDRepDeg());
					message.append("\n");
					message.append("- Perceived Replication Degree: ");
					message.append(storedChunks.get(i).getPRepDeg());
					message.append("\n");
				}
			}
			
			if(backedUpChunks.size() != 0){
				message.append("\nBackuped Chunks\n");
				for(int j=0; j<backedUpChunks.size(); j++) {
					message.append("File pathname: ");
					message.append(backedUpChunks.get(j).getStorePath());
					message.append("\n");
					message.append("- Chunk ID: ");
					message.append(backedUpChunks.get(j).getChunkId());
					message.append("\n");
					message.append("- Backup ID: ");
					message.append(backedUpChunks.get(j).getServiceID());
					message.append("\n");
					message.append("- Desired Replication Degree: ");
					message.append(backedUpChunks.get(j).getDRepDeg());
					message.append("\n");
					message.append("- Chunk Perceived Replication Degree: ");
					message.append(backedUpChunks.get(j).getPRepDeg());
					message.append("\n");
				}
			}
		
			return new String(message);
		}
	}
}
