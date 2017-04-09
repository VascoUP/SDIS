package information;

import java.io.File;
import java.security.MessageDigest;

/**
 * 
 * This class builds a chunk
 * This implements the Comparable interface
 *
 */
public abstract class Chunk implements Comparable<Chunk> {
	protected String storePath;		//Path where the chunks will be placed
	protected String fileId;		//File ID
	protected int chunkId;			//Chunk ID
	protected byte[] chunk;			//Chunk's content
	
	/**
	 * Chunk's constructor using the store path, file's ID and chunk's ID
	 * @param storePath Path where the chunks will be placed
	 * @param fileId File's ID
	 * @param chunkId Chunk's ID
	 */
	public Chunk(String storePath, String fileId, int chunkId) {
		this.storePath = storePath;
		this.fileId = fileId;
		this.chunkId = chunkId;
		this.chunk = new byte[0]; //Chunk's content
	}

	/**
	 * Chunk's constructor using the store path, file's ID, chunk's ID and chunk's content
	 * @param storePath Path where the chunks will be placed
	 * @param fileId File's ID
	 * @param chunkId Chunk's ID
	 * @param chunk Chunk's content
	 */
	public Chunk(String storePath, String fileId, int chunkId, byte[] chunk) {
		this.storePath = storePath;
		this.fileId = fileId;
		this.chunkId = chunkId;
		this.chunk = chunk;
	}

	/**
	 * Gets the chunk's content	
	 * @return The chunk's content
	 */
	public byte[] getChunk() {
		return chunk;
	}

	/**
	 * Gets the chunk's ID
	 * @return The chunk's ID
	 */
	public int getChunkId() {
		return chunkId;
	}
	
	/**
	 * Gets the file's ID
	 * @return The file's ID
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * Gets the store path
	 * @return The store path
	 */
	public String getStorePath() {
		return storePath;
	}

	/**
	 * Gets the file's ID from a path name
	 * @param path File's path
	 * @return The file's ID from a path name
	 */
	public static String getFileId(String path) {
		File file = new File(path);
		String base = path + file.lastModified() + file.length(); //Starting to creating the file's ID	
		
	    try{
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(base.getBytes("UTF-8"));
	        StringBuffer hexString = new StringBuffer();

	        for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();
	    } catch(Exception ex){
	       throw new RuntimeException(ex);
	    }
	}

	/**
	 * Sets the chunk's content
	 * @param chunk New content that will be placed in the chunk's content
	 */
	public void setChunk(byte[] chunk) {
		this.chunk = chunk;
	}
	
	/**
	 * Sets the chunk's ID
	 * @param chunkId New chunk's ID
	 */
	public void setChunkId(int chunkId) {
		this.chunkId = chunkId;
	}
	
	/**
	 * Sets the file's ID
	 * @param fileId New file's ID
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	/**
	 * Sets the store path
	 * @param storePath New store path
	 */
	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}
	
	/**
	 * 
	 * Verifies if chunks have the same file's ID and the same chunk's ID
	 * @param o Object that will be compared
	 * @return true if the chunks have the same file's ID and chunk's ID, false otherwise
	 * 
	 */
	@Override
	public boolean equals(Object o) {
		Chunk c = (Chunk) o;
		return 	c.getFileId().equals(fileId) &&
				c.getChunkId() == chunkId;
	}
	
	/**
	 * Compares this object with the specified object for order
	 * @param Chunk that will be compared
	 * @return  Returns a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object
	 */
	@Override
    public int compareTo(Chunk chunk) {
        return chunkId - chunk.getChunkId();
    }
}
