package threads;

/**
 * 
 * Thread pool interface
 *
 */
public interface ThreadPool {
	/**
	 * Shuts down the thread pool
	 */
	public void shutdown();
	/**
	 * Start a new thread
	 * @param worker This is used by any class whose instances are intended to be executed by a thread
	 */
	public void startNewThread(Runnable worker);
}
