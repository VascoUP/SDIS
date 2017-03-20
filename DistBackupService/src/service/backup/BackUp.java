package service.backup;

import java.io.IOException;

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
}
