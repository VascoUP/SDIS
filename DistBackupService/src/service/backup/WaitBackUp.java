package service.backup;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import protocol.backup.AnswerBackUp;

public class WaitBackUp implements Runnable {

	private AnswerBackUp abu;
	
	public WaitBackUp() throws IOException {
		abu = new AnswerBackUp();
	}
	
	private void randomWait() throws InterruptedException {
		Random r = new Random();
		int wait = r.nextInt(400);
		TimeUnit.MILLISECONDS.sleep(wait);
	}
	
	public void backup_file() throws IOException, InterruptedException  {
		String rcv = "";
	
		rcv = abu.receive();
		System.out.println("Rcv: " + rcv);
		
		randomWait();
		
		abu.setMessage(1, 1);
		abu.send();
	}
	
	public void end() throws IOException {
		abu.close();
	}

	@Override
	public void run() {
		while( true ) {
			try {
				backup_file();
			} catch (IOException | InterruptedException e) {
				return ;
			}
		}
	}
}
