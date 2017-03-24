package service.restore;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import protocol.restore.SendChunk;

public class WaitRestore implements Runnable {
	private SendChunk sc;
	
	public WaitRestore() throws IOException {
		sc = new SendChunk();
	}
	
	private void randomWait() throws InterruptedException {
		Random r = new Random();
		int wait = r.nextInt(400);
		TimeUnit.MILLISECONDS.sleep(wait);
	}
	
	public void restore_file() throws IOException, InterruptedException  {
		String rcv = "";
	
		rcv = sc.receive();
		System.out.println("Rcv: " + rcv);
		
		randomWait();
		
		//sc.setMessage(1, 1);
		sc.send();
	}
	
	public void end() throws IOException {
		sc.close();
	}

	@Override
	public void run() {
		while( true ) {
			try {
				restore_file();
			} catch (IOException | InterruptedException e) {
				return ;
			}
		}
	}
}
