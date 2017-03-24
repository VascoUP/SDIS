package service.restore;

import java.io.IOException;

import protocol.restore.GetChunk;

public class Restore implements Runnable {
	private GetChunk gc;
	
	public Restore() throws IOException {
		gc = new GetChunk();
	}
	
	private int wait_answers() {
		int i = 0;
		
		long t = System.currentTimeMillis();
		long end = t + 1000;

		String rcv = "";
		
		while((t = end - System.currentTimeMillis()) > 0 ){
			gc.socketTimeout((int)t);
			
			try {
				rcv = gc.receive();
			} catch (IOException e) {
				break;
			}
			
			System.out.println(rcv);
			i++;
		}
		
		return i;
	}
	
	public void restore_file() {
		try {
			gc.send();
		} catch (IOException e) {
			System.out.println("Error sending chunk");
			return ;
		}
		
		wait_answers();
		
		//If number of rcv is lower than expected, resend the same chunk
		//If not, advance to the next one

		String rcv = "Didnt receive chunk response";
		try {
			rcv = gc.receive();
		} catch (IOException e) {
		}
		
		System.out.println(rcv);
	}
	
	public void end() {
		try {
			gc.close();
		} catch (IOException e) {
			System.out.println("Error closing");
		}
	}

	@Override
	public void run() {
		 restore_file();
		 end();
	}
}
