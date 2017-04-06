package service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import message.BasicMessage;

public abstract class MessageServiceWait extends MessageService {
	public static final int MAX_WAIT_TIME = 400;
	
	public MessageServiceWait(long time, BasicMessage message) {
		super(time, message);
	}
	
	protected boolean condition() {
		System.err.println("Execution in the wrong class");
		return false;
	}

	protected void wait(int time) {
		try { TimeUnit.MILLISECONDS.sleep(time);
		} catch (InterruptedException e) {
		}
	}
	
	protected int randomTime() {
		long currTime = System.currentTimeMillis();
		long dTime = currTime - time;
		int maxWaitTime = MAX_WAIT_TIME - (int)dTime;
		if( maxWaitTime < 0 )
			return -1;
		Random r = new Random();
		return r.nextInt(maxWaitTime);
	}
	
	protected boolean randomWait() {
		int time = randomTime();
		if( time < 0 )
			return false;
		wait(time);
		return true;
	}
	
	protected void service() {
		System.err.println("WaitMessageService: service wrong class");
	}
	
	public void start() {
		if( !randomWait() )
			return ;
		
		if( condition() )
			service();
	}
}
