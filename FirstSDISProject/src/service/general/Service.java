package service.general;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import message.general.Message;
import protocol.general.Protocol;

public abstract class Service implements Runnable {
	protected Protocol protocol;
	
	public Service() {
		
	}
	
	
	public byte[] receive() {
		return receive(protocol);
	}
	
	public byte[] receive(int time) {
		return receive(protocol, time);
	}
	
	public boolean send() {
		return send(protocol);
	}

	
	public static byte[] receive(Protocol p) {
		byte[] rcv;
				
		try {
			rcv = p.receive();
		} catch (IOException e) {
			return null;
		}
		
		return rcv;
	}
	
	public static byte[] receive(Protocol p, int time) {
		p.socketTimeout(time);	
		return receive(p);
	}
	
	public static boolean send(Protocol p) {
		try {
			p.send();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	
	public void run_service() throws IOException, InterruptedException {
		System.out.println("Running on service class error");
	}
	
	public void end_service() {
		try {
			protocol.end_protocol();
		} catch (IOException e) {
		}
	}
	
	@Override
	public void run() {
		System.out.println("Running on service class error");
	}

	
	public void randomWait() throws InterruptedException {
		int wait = randomTime();
		TimeUnit.MILLISECONDS.sleep(wait);
	}
	
	public int randomTime() {
		Random r = new Random();
		return r.nextInt(400);
	}
	
	public Message validateMessage(byte[] message) {
		return null;
	}
	
}
