package service.backup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import message.MessageConst;
import protocol.backup.RequestBackUp;

public class BackUp implements Runnable {
	private String filePath;
	private RequestBackUp rbu;
	
	public BackUp(String filePath) throws IOException {
		rbu = new RequestBackUp();
		this.filePath = filePath;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	private int wait_answers() {
		int i = 0;
		
		long t = System.currentTimeMillis();
		long end = t + 1000;

		String rcv = "";
		
		while((t = end - System.currentTimeMillis()) > 0 ){
			rbu.socketTimeout((int)t);
			
			try {
				rcv = rbu.receive();
			} catch (IOException e) {
				break;
			}
			
			System.out.println(rcv);
			i++;
		}
		
		return i;
	}
	
	public void backup_file() {
		try {
			rbu.send();
		} catch (IOException e) {
			System.out.println("Error sending chunk");
			return ;
		}
		
		wait_answers();
		
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
		 try {
			createChunks();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createChunks() throws IOException{
		
		byte[] buffer = new byte[MessageConst.CHUNKSIZE]; //64000

		FileInputStream input;
		
		try {
			input = new FileInputStream(this.filePath);			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage() + this.filePath);
			return;
		}
		

		int IDchunk = 1;
		int chunkSize = 0;
		
		while ((chunkSize = input.read(buffer)) > 0) {			
			byte[] newBuffer = Arrays.copyOf(buffer, chunkSize);
			rbu.setMessage(1, IDchunk, newBuffer);
			backup_file();
	        
			IDchunk++;		
		}
		
		input.close();

		try {
			rbu.close();
		} catch (IOException e) {
			System.out.println("Error closing sockets");
			return ;
		}
	}
}
