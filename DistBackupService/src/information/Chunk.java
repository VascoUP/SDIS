package information;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import file.HandleFile;

public class Chunk {
	private String storePath;
	private String fileId;
	private int chunkId;
	private byte[] chunk;
	
	public Chunk(String storePath, String fileId, int chunkId, byte[] chunk) {
		this.storePath = storePath;
		this.fileId = fileId;
		this.chunkId = chunkId;
		this.chunk = chunk;
	}
	
	public Chunk(String storePath, String fileId, int chunkId) {
		this.storePath = storePath;
		this.fileId = fileId;
		this.chunkId = chunkId;
		this.chunk = null;
		
	}
	
	public String getStorePath() {
		return storePath;
	}

	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public int getChunkId() {
		return chunkId;
	}

	public void setChunkId(int chunkId) {
		this.chunkId = chunkId;
	}

	public byte[] getChunk() {
		return chunk;
	}

	public void setChunk(byte[] chunk) {
		this.chunk = chunk;
	}
	
	
	public void store() throws IOException {
		HandleFile.writeFile(chunk, storePath);
		storeAppInfo();
	}
	
	public void backUp() throws IOException {
		backUpAppInfo();
	}
	
	public void storeAppInfo() {
		AppInfo.storeChunk(this);		
	}
	

	public void backUpAppInfo() {
		AppInfo.backupChunk(this);		
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
	
	
	@Override
	public boolean equals(Object o) {
		Chunk c = (Chunk) o;
		return 	c.getFileId().equals(fileId) &&
				c.getChunkId() == chunkId;
	}
}
