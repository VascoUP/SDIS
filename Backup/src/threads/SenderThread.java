package threads;

import sender.ChannelSender;

/**
 * 
 * This class creates a sender thread
 * This implements the ThreadOperations interface
 *
 */
public class SenderThread implements ThreadOperations{
	private Thread thread;				//Thread
	private ChannelSender sender;		//Channel's sender
	
	/**
	 * SenderThread's constructor
	 * @param thread Thread to be used
	 * @param sender Sender to be associated with the thread
	 */
	public SenderThread(Thread thread, ChannelSender sender) {
		this.thread = thread;
		this.sender = sender;
		thread.setName("" + sender); //Sets the thread name
	}

	/**
	 * Closes the sender channel
	 * @throws InterruptedException Thrown when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity
	 */
	@Override
	public void close() throws InterruptedException {
		join();
		sender.closeChannel();
	}
	
	/**
	 * Gets the thread's name
	 * @param The thread's name
	 */
	@Override
	public String getName() {
		return thread.getName();
	}

	/**
	 * Gets the sender
	 * @return The sender
	 */
	public ChannelSender getSender() {
		return sender;
	}

	/**
	 * Interrupts the thread execution
	 * @throws InterruptedException Thrown when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity
	 */
	@Override
	public void interrupt() throws InterruptedException {
		throw new InterruptedException("Sender threads cannot be interrupted");
	}
	
	/**
	 * Verifies if the thread is "alive"
	 * @return true if the thread is alive, false otherwise
	 */
	@Override
	public boolean isAlive() {
		return thread.isAlive();
	}

	/**
	 * Joins the sender thread
	 * @throws InterruptedException Thrown when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity
	 */
	@Override
	public void join() throws InterruptedException {
		thread.join();
	}

	/**
	 * Starts the sender thread
	 */
	@Override
	public void start() {
		thread.start();
	}
}
