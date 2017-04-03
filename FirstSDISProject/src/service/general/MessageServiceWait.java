package service.general;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import message.BasicMessage;

public abstract class MessageServiceWait extends MessageService {
	public MessageServiceWait(long time, BasicMessage message) {
		super(time, message);
	}
	
	public void wait(int time) {
		try { TimeUnit.MILLISECONDS.sleep(time);
		} catch (InterruptedException e) {
		}
	}
	
	public int randomTime() {
		Random r = new Random();
		return r.nextInt(400);
	}
	
	public void randomWait() {
		int time = randomTime();
		wait(time);
	}
	
	public boolean condition() {
		return false;
	}
	
	public void service() {
		System.err.println("WaitMessageService: service wrong class");
	}
	
	public void start() {
		randomWait();
		if( condition() )
			service();
	}
}
