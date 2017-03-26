package service.general;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import protocol.general.Protocol;

public abstract class Service implements Runnable {
	protected Protocol protocol;
	
	public Service() {
		
	}
	
	
	protected void randomWait() throws InterruptedException {
		Random r = new Random();
		int wait = r.nextInt(400);
		TimeUnit.MILLISECONDS.sleep(wait);
	}
	
	
	public boolean validateMessage(byte[] message) {
		return false;
	}

	protected int wait_answers() {
		int i = 0;
		
		long t = System.currentTimeMillis();
		long end = t + 1000;

		byte[] rcv;
		
		while((t = end - System.currentTimeMillis()) > 0 ){
			protocol.socketTimeout((int)t);
			
			try {
				rcv = protocol.receive();
			} catch (IOException e) {
				break;
			}
			
			if(validateMessage(rcv))
				i++;
		}
		
		return i;
	}
	
	
	protected void run_pontual_service() {
		try {
			protocol.send();
		} catch (IOException e) {
			System.out.println("Error sending chunk");
			return ;
		}
		
		wait_answers();
	}
	
	protected void run_continuous_service() throws IOException, InterruptedException {
		System.out.println("Running on service class error");
	}
	
	protected void run_continuous() {
		while( true ) {
			try {
				run_continuous_service();
			} catch (IOException | InterruptedException e) {
				return ;
			}
		}
	}

	
	public void end_service() {
		try {
			protocol.end_protocol();
		} catch (IOException e) {
			System.out.println("Error closing");
		}
	}
	
	@Override
	public void run() {
		run_pontual_service();
		end_service();
	}

}
