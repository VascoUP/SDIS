package service.general;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import protocol.general.Protocol;

public abstract class Service implements Runnable {
	protected Protocol protocol;
	
	public Service() {
		
	}
	
	public void randomWait() throws InterruptedException {
		Random r = new Random();
		int wait = r.nextInt(400);
		TimeUnit.MILLISECONDS.sleep(wait);
	}
	
	public boolean validateMessage(byte[] message) {
		return false;
	}
	
	public void run_service() throws IOException, InterruptedException {
		System.out.println("Running on service class error");
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
		System.out.println("Running on service class error");
	}

}
