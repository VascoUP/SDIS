package information;

import java.io.File;
import java.security.MessageDigest;

public class Chunk implements Comparable<Chunk> {
	private final String storePath;
	private final String fileId;
	private final int chunkId;

	public Chunk(String storePath, String fileId, int chunkId) {
		this.storePath = storePath;
		this.fileId = fileId;
		this.chunkId = chunkId;
	}

	public void backUpAppInfo() {
		FileInfo.backupChunk(this);		
	}

	@Override
	public boolean equals(Object o) {
		Chunk c = (Chunk) o;
		return 	o.getClass() == Chunk.class &&
                c.getFileId().equals(fileId) &&
				c.getChunkId() == chunkId;
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


	public void storeAppInfo() {
		FileInfo.storeChunk(this);		
	}

	
	public static String getFileId(String path) {
		File file = new File(path);
		String base = path + file.lastModified() + file.length();		
		
	    try{
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(base.getBytes("UTF-8"));
	        StringBuilder hexString = new StringBuilder();

			for (byte aHash : hash) {
				String hex = Integer.toHexString(0xff & aHash);
				if (hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}

	        return hexString.toString();
	    } catch(Exception ex){
	       throw new RuntimeException(ex);
	    }
	}

	
	@Override
    public int compareTo(Chunk chunk) {
        return chunkId - chunk.getChunkId();
    }
}
