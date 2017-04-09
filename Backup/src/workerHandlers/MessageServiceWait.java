package workerHandlers;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import information.MessagesHashmap;
import message.BasicMessage;

/**
 * 
 * This class builds the message's service for waiting
 * This extends the MessageService class
 *
 */
public abstract class MessageServiceWait extends MessageService {
	public static final int MAX_WAIT_TIME = 400;		//Maximum waiting time
	
	/**
	 * MessageServiceWait's constructor
	 * @param time Message service's time
	 * @param message Basic Message
	 */
	public MessageServiceWait(long time, BasicMessage message) {
		super(time, message);
	}
	
	/**
	 * Verifies if the program is executing the wrong class
	 * @return false if the program is executing the wrong class, false otherwise
	 */
	protected boolean condition() {
		System.err.println("Execution in the wrong class");
		return false;
	}

	/**
	 * Makes the thread sleep a certain time
	 * @param time Time to put asleep the thread
	 */
	protected void wait(int time) {
		try { TimeUnit.MILLISECONDS.sleep(time);
		} catch (InterruptedException e) {
		}
	}
	
	/**
	 * Gets a random time to be used when we calculate the random wait time
	 * @return The random time calculated
	 */
	protected int randomTime() {
		long currTime = System.currentTimeMillis();
		long dTime = currTime - time;
		int maxWaitTime = MAX_WAIT_TIME - (int)dTime;
		if( maxWaitTime < 0 )
			return -1;
		Random r = new Random();
		return r.nextInt(maxWaitTime);
	}
	
	/**
	 * Gets a random waiting time
	 * @return The random waiting time
	 */
	protected boolean randomWait() {
		int time = randomTime();
		if( time < 0 )
			return false;
		wait(time);
		return true;
	}
	
	/**
	 * Creates the service
	 */
	protected void service() {
		System.err.println("WaitMessageService: service wrong class");
	}
	
	/**
	 * Starts the service
	 */
	public void start() {
		MessagesHashmap.addMessage(message);
		if( !randomWait() && condition() )
			service();
	}
}
