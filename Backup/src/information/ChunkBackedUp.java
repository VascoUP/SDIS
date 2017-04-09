package information;

/**
 * 
 * This class builds the chunk backed up
 * This extends the class Chunk
 *
 */
public class ChunkBackedUp extends Chunk {
	private int serviceID;		//Service's ID
	private int drepdeg;		//Desired replication degree
	private int prepdeg;		//Perceived replication degree
	
	/**
	 * ChunkBackedUp's constructor
	 * @param serviceID Service's ID
	 * @param storePath Store path
	 * @param fileID File's ID
	 * @param chunkID Chunk's ID
	 * @param drepdeg Desired replication degree
	 * @param prepdeg Perceived replication degree
	 */
	public ChunkBackedUp(int serviceID, String storePath, String fileID, int chunkID, int drepdeg, int prepdeg) {
		super(storePath, fileID, chunkID);
		this.serviceID = serviceID;
		this.drepdeg = drepdeg;
		this.prepdeg = prepdeg;
	}
	
	/**
	 * ChunkBackedUp's constructor
	 * @param storePath Store path
	 * @param fileID File's ID
	 * @param chunkID Chunk's ID
	 * @param drepdeg Desired replication degree
	 * @param prepdeg Perceived replication degree
	 */
	public ChunkBackedUp(String storePath, String fileID, int chunkID, int drepdeg, int prepdeg) {
		super(storePath, fileID, chunkID);
		this.serviceID = PeerInfo.peerInfo.getServerID(); //Gets the service's ID from the peer's information
		this.drepdeg = drepdeg;
		this.prepdeg = prepdeg;
	}
	
	/**
	 * Gets the service's ID
	 * @return The service's ID
	 */
	public int getServiceID() {
		return serviceID;
	}
	
	/**
	 * Gets the desired replication degree
	 * @return The desired replication degree
	 */
	public int getDRepDeg() {
		return drepdeg;
	}
	
	/**
	 * Gets the perceived replication degree
	 * @return The perceived replication degree
	 */
	public int getPRepDeg() {
		return prepdeg;
	}
}
