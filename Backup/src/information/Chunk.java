package information;

import java.io.File;
import java.security.MessageDigest;


public abstract class Chunk implements Comparable<Chunk> {
	protected String storePath;
	protected String fileId;
	protected int chunkId;
	protected byte[] chunk;
	
	public Chunk(String storePath, String fileId, int chunkId) {
		this.storePath = storePath;
		this.fileId = fileId;
		this.chunkId = chunkId;
		this.chunk = new byte[0];
	}

	public Chunk(String storePath, String fileId, int chunkId, byte[] chunk) {
		this.storePath = storePath;
		this.fileId = fileId;
		this.chunkId = chunkId;
		this.chunk = chunk;
	}

		
	public byte[] getChunk() {
		return chunk;
	}

	public int getChunkId() {
		return chunkId;
	}
	
	public String getFileId() {
		return fileId;
	}

	public String getStorePath() {
		return storePath;
	}

	public static String getFileId(String path) {
		File file = new File(path);
		String base = path + file.lastModified() + file.length();		
		
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

	
	public void setChunk(byte[] chunk) {
		this.chunk = chunk;
	}
	
	public void setChunkId(int chunkId) {
		this.chunkId = chunkId;
	}
	
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}
	

	@Override
	public boolean equals(Object o) {
		Chunk c = (Chunk) o;
		return 	c.getFileId().equals(fileId) &&
				c.getChunkId() == chunkId;
	}
	
	@Override
    public int compareTo(Chunk chunk) {
        return chunkId - chunk.getChunkId();
    }
}
