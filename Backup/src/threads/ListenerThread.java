package threads;

import listener.ChannelListener;

/**
 * 
 * This class creates a thread to the listener
 * This implements the ThreadOperations interface
 *
 */
public class ListenerThread implements ThreadOperations {
	private Thread thread;				//Thread
	private ChannelListener listener;	//Channel listener
	
	/**
	 * ListenerThread's constructor
	 * @param thread Thread to be used
	 * @param listener Listener to be associated with the thread
	 */
	public ListenerThread(Thread thread, ChannelListener listener) {
		this.thread = thread;
		this.listener = listener;
	}
	
	/**
	 * Closes the listener channel
	 * @throws InterruptedException Thrown when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity
	 */
	@Override
	public void close() throws InterruptedException {
		interrupt();
		listener.closeChannel();
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
	 * Interrupts the thread execution
	 * @throws InterruptedException Thrown when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity
	 */
	@Override
	public void interrupt() throws InterruptedException {
		thread.interrupt();
		
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
	 * Joins the listener thread
	 * @throws InterruptedException Thrown when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity
	 */
	@Override
	public void join() throws InterruptedException {
		throw new InterruptedException("Listener threads cannot be joined");
	}

	/**
	 * Starts the listener thread
	 */
	@Override
	public void start() {
		thread.start();
	}
}

