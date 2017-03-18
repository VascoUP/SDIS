package service.backup;

import java.io.IOException;
import java.net.SocketTimeoutException;

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
			
			try {
				rcv = rbu.receive();
			} catch(SocketTimeoutException e) {
				break;
			}
			
			System.out.println(rcv);
			i++;
		}
		
		return i;
	}
	
	public void backup_file() throws IOException {
		rbu.send();
		int i = wait_answers();
		System.out.println("" + i);
		
		//If number of rcv is lower than expected, resend the same chunk
		//If not, advance to the next one
		
		this.rbu.close();
		
		
	}

	@Override
	public void run() {
		try {
			backup_file();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
