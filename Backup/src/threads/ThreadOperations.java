package threads;

/**
 * 
 * This interface builds the threads operations
 *
 */
public interface ThreadOperations {
	/**
	 * Closes the thread
	 * @throws InterruptedException Thrown when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity
	 */
	public void close() throws InterruptedException;
	/**
	 * Gets the thread's name
	 * @return The thread's name
	 */
	public String getName();
	/**
	 * Interrupts the thread's execution
	 * @throws InterruptedException
	 */
	public void interrupt() throws InterruptedException;
	/**
	 * Verifies if a thread is "alive"
	 * @return true if the thread is alive, false otherwise
	 */
	public boolean isAlive();
	/**
	 * Joins the thread
	 * @throws InterruptedException Thrown when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity
	 */
	public void join() throws InterruptedException;
	/**
	 * Starts the thread
	 */
	public void start();
}
