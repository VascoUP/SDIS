package information;

public class ChunkStored extends Chunk {
	private int prepdeg;
	private int size;
	
	public ChunkStored(String storePath, String fileId, int chunkId, int prepdeg, byte[] chunk) {
		super(storePath, fileId, chunkId, chunk);
		this.prepdeg = prepdeg;
		this.size = chunk.length;
	}
	
	public ChunkStored(String storePath, String fileId, int chunkId, int prepdeg, int size) {
		super(storePath, fileId, chunkId);
		this.prepdeg = prepdeg;
		this.size = size;
	}
	
	public int getPRepDeg() {
		return prepdeg;
	}
	
	public int getSize() {
		return size;
	}
}
