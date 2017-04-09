package threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 
 * This class creates a worker pool
 * This implements the ThreadPool interface
 *
 */
public class WorkerPool implements ThreadPool {
	private static final int NUMBER_THREADS = 10;		//Maximum number of threads
	
	private ExecutorService service;					//Service's executor
	private int nThreads = 0;							//Atual number of threads
	
	/**
	 * WorkerPool's constructor
	 */
	public WorkerPool() {
		service = Executors.newFixedThreadPool(NUMBER_THREADS);
	}

	/**
	 * Shuts down the worker pool
	 */
	@Override
	public void shutdown() {
		try {
		    System.out.println("Attempt to shutdown executor");
		    service.shutdown();
		    service.awaitTermination(5, TimeUnit.SECONDS);
		}
		catch (InterruptedException e) {
		    System.err.println("Tasks interrupted");
		}
		finally {
		    if (!service.isTerminated()) {
		        System.err.println("Cancel non-finished tasks");
		    }
		    service.shutdownNow();
		}
	}
	
	/**
	 * Starts a new thread
	 * @param worker This is used by any class whose instances are intended to be executed by a thread
	 */
	@Override
	public void startNewThread(Runnable worker) {
		service.execute(worker);	
	}
	
	/**
	 * Starts all worker threads
	 */
	public void startAllWorkerThreads() {
		for( int i = 0; i < NUMBER_THREADS; i++ ) {
			nThreads++;
			startNewWorkerThread();
		}
	}
	
	/**
	 * Starts a new worker thread
	 */
	public void startNewWorkerThread() {
		Runnable worker = new WorkerThread(nThreads);
		startNewThread(worker);	
	}
}
