package service.backup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import information.Storable;
import message.MessageConst;
import protocol.backup.RequestBackUp;
import service.general.Service;

public class BackUp extends Service implements Storable {
	private String filePath;
	
	public BackUp(String filePath) throws IOException {
		super();
		
		protocol = new RequestBackUp();
		this.filePath = filePath;
	}
	
	public String getFilePath() {
		return filePath;
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
	
		RequestBackUp rbu = (RequestBackUp) protocol;
		
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
		
		while ((chunkSize = input.read(buffer)) != -1) {
			byte[] newBuffer = new byte[chunkSize];
			System.arraycopy(buffer, 0, newBuffer, 0, chunkSize);
			
			rbu.setMessage(1, IDchunk, newBuffer);
			run_pontual_service();
	        
			IDchunk++;		
		}
		
		input.close();

		try {
			rbu.end_protocol();
		} catch (IOException e) {
			System.out.println("Error closing sockets");
			return ;
		}
	}
}
