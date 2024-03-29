package information;

/**
 * 
 * This class builds the stored chunks
 * This extends the class Chunk
 *
 */
public class ChunkStored extends Chunk {
	private int prepdeg;	//Perceived replication degree
	private int drepdeg;	//Desired replication degree
	private int size;		//Stored chunk size
	
	/**
	 * ChunkStored's constructor
	 * @param storePath Store path
	 * @param fileId File's ID
	 * @param chunkId Chunk's ID
	 * @param prepdeg Perceived replication degree
	 * @param chunk Chunk's content
	 */
	public ChunkStored(String storePath, String fileId, int chunkId, int drepdeg, int prepdeg, byte[] chunk) {
		super(storePath, fileId, chunkId, chunk);
		this.drepdeg = drepdeg;
		this.prepdeg = prepdeg;
		this.size = chunk.length;
	}
	
	/**
	 * ChunkStored's constructor
	 * @param storePath Store path
	 * @param fileId File's ID
	 * @param chunkId Chunk's ID
	 * @param prepdeg Perceived replication degree
	 * @param size Stored chunk's size
	 */
	public ChunkStored(String storePath, String fileId, int chunkId, int drepdeg, int prepdeg, int size) {
		super(storePath, fileId, chunkId);
		this.drepdeg = drepdeg;
		this.prepdeg = prepdeg;
		this.size = size;
	}
	
	/**
	 * Gets the perceived replication degree
	 * @return The perceived replication degree
	 */
	public int getPRepDeg() {
		return prepdeg;
	}
	
	/**
	 * Gets the desired replication degree
	 * @return The desired replication degree
	 */
	public int getDRepDeg() {
		return drepdeg;
	}
	
	/**
	 * Gets the stored chunk's size
	 * @return The stored chunk's size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Sets the variable prepdeg to a new value
	 * @param prepdeg The new value of prepdeg
	 */
	public void setPRepDeg(int prepdeg) {
		this.prepdeg = prepdeg;
	}
}
