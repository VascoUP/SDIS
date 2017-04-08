package threads;

import workerHandlers.ServiceParser;

/**
 * 
 * This class builds a worker thread
 * This implements the Runnable interface
 *
 */
public class WorkerThread implements Runnable {
	private final int id;				//Worker thread's ID
	
	/**
	 * WorkerThread's constructor
	 * @param id Worker thread's ID
	 */
	public WorkerThread(int id) {
		this.id = id;
	}
	
	/**
	 * Runs the current thread
	 */
	@Override
	public void run() {
		Thread.currentThread().setName("" + id);
		while( !Thread.currentThread().isInterrupted() )
			ServiceParser.parseService();
	}
}
