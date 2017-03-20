package chunk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Chunk {
	private String name;
	private int chunkID;
	private int repDegree;
	private byte[] chunkData;

	public Chunk(String name, int chunkID, int repDegree, byte[] chunkData) throws FileNotFoundException, IOException{
		this.name = name;
		this.chunkID = chunkID;
		this.repDegree = repDegree;
		this.chunkData = chunkData;
		
		createChunkFile();
    }
	
	public void createChunkFile() throws FileNotFoundException, IOException{
		File newFile = new File(this.name);
        try (FileOutputStream outputStream = new FileOutputStream(newFile)) {
            outputStream.write(this.chunkData);
        }
	}

}

/*
 * 
vector<Chunk>chunks;

byte[] buffer = new byte[MAX_CHUNK_SIZE]; //64000

try (FileInputStream input = new FileInputStream(filename) {

        int chunkID = 1;
	int chunkSize = 0;

        while ((chunkSize = input.read(buffer)) >= 0) {

		String chunkPathName = chunkID + "-" + filename;
		byte[] newBuffer = Array.copyOf(buffer, MAX_CHUNK_SIZE);
                Chunk chunk =  new Chunk(chunkPathName, chunkID, repdegree, newBuffer);
                chunks.add(chunk);
		
		chunkID++;
		
        }
}*/
