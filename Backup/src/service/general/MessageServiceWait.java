package service.general;

import message.BasicMessage;

import java.util.Random;
import java.util.concurrent.TimeUnit;

abstract class MessageServiceWait extends MessageService {
	private static final int MAX_WAIT_TIME = 400;
	
	MessageServiceWait(long time, BasicMessage message) {
		super(time, message);
	}

    private void wait(int time) {
		try { TimeUnit.MILLISECONDS.sleep(time);
		} catch (InterruptedException ignored) {
		}
	}
	
	private int randomTime() {
		long currTime = System.currentTimeMillis();
		long dTime = currTime - time;
		int maxWaitTime = MAX_WAIT_TIME - (int)dTime;
		if( maxWaitTime < 0 )
			return -1;
		Random r = new Random();
		return r.nextInt(maxWaitTime);
	}
	
	private boolean randomWait() {
		int time = randomTime();
		if( time < 0 )
			return false;
		wait(time);
		return true;
	}

	public boolean condition() {
		return false;
	}

	public void service() {
    }

    void start() {
		if( !randomWait() )
			return ;
		
		if( condition() )
			service();
		else 
			System.out.println("didnt pass the condition");
	}
}
