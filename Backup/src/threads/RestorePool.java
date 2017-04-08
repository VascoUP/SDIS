package threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import sender.ChannelSender;

/**
 * 
 * This class creates a thread to the restore process
 * This implements the ThreadPool interface
 *
 */
public class RestorePool implements ThreadPool {
	private static final int NUMBER_THREADS = 5;	//Maximum number of threads
	
	private ExecutorService service;				//Service's executor
	
	/**
	 * RestorePool's constructor
	 */
	public RestorePool() {
		service = Executors.newFixedThreadPool(NUMBER_THREADS);
	}
	
	/**
	 * Shuts down the restore thread
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
	 * Starts a new restore thread
	 * @param worker This is used by any class whose instances are intended to be executed by a thread
	 */
	@Override
	public void startNewThread(Runnable worker) {
		service.execute(worker);
	}

	/**
	 * Starts the restore's thread
	 * @param sender Channel to be used when we start a new thread to do the restore
	 */
	public void startRestoreThread(ChannelSender sender) {
		startNewThread(sender);
	}
}
