package information;

public class ChunkBackedUp extends Chunk {
	private int serviceID;
	private int drepdeg;
	private int prepdeg;
	
	public ChunkBackedUp(int serviceID, String storePath, String fileID, int chunkID, int drepdeg, int prepdeg) {
		super(storePath, fileID, chunkID);
		this.serviceID = serviceID;
		this.drepdeg = drepdeg;
		this.prepdeg = prepdeg;
	}
	
	public ChunkBackedUp(String storePath, String fileID, int chunkID, int drepdeg, int prepdeg) {
		super(storePath, fileID, chunkID);
		this.serviceID = PeerInfo.peerInfo.getServerID();
		this.drepdeg = drepdeg;
		this.prepdeg = prepdeg;
	}
	
	public int getServiceID() {
		return serviceID;
	}
	
	public int getDRepDeg() {
		return drepdeg;
	}
	
	public int getPRepDeg() {
		return prepdeg;
	}
}
