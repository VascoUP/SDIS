package service.backup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import message.backup.*;
import protocol.backup.RequestBackUp;

public class BackUp implements Runnable {
	private String filePath;
	private RequestBackUp rbu;
	private ArrayList<StoreChunkMessage>chunks = new ArrayList<StoreChunkMessage>();
	
	public BackUp(String filePath) throws IOException {
		rbu = new RequestBackUp();
		this.filePath = filePath;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	private int wait_answers() throws IOException {
		int i = 0;
		
		long t = System.currentTimeMillis();
		long end = t + 1000;

		String rcv;
		
		while((t = end - System.currentTimeMillis()) > 0 ){
			rbu.socketTimeout((int)t);
			
			rcv = rbu.receive();
			
			System.out.println(rcv);
			i++;
		}
		
		return i;
	}
	
	public void backup_file() {
		try {
			rbu.send();
			
			wait_answers();
			
			rbu.close();
		} catch (IOException e) {
			System.out.println("Error during backup protocol");
			return ;
		}
		
		//If number of rcv is lower than expected, resend the same chunk
		//If not, advance to the next one
		
	}
	
	public void end() {
		try {
			rbu.close();
		} catch (IOException e) {
			System.out.println("Error closing");
		}
	}

	@Override
	public void run() {
		backup_file();
	}
	
	public void createChunks() throws IOException{
		
		byte[] buffer = new byte[ChunkConst.CHUNKSIZE]; //64000

		try {
			FileInputStream input = new FileInputStream(this.filePath);
			
			int IDchunk = 1;
			int chunkSize = 0;

			while ((chunkSize = input.read(buffer)) >= 0) {

				String chunkPathName = IDchunk + "-" + this.filePath;
				byte[] newBuffer = Arrays.copyOf(buffer, chunkSize);
				StoreChunkMessage chunk =  new StoreChunkMessage("1", 1, 1, IDchunk, newBuffer);
				chunks.add(chunk);
				
				File newFile = new File(chunkPathName);
				FileOutputStream output = new FileOutputStream(newFile);
		        output.write(newBuffer);
		        
		        output.close();
		        
				IDchunk++;		
			}
			
			input.close();
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage() + this.filePath);
		}
	}
}
