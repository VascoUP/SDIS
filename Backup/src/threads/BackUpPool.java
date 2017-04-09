package threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import sender.ChannelSender;

/**
 * 
 * This class creates a thread to do the backup
 * This implements the ThreadPool interface
 *
 */
public class BackUpPool implements ThreadPool {
	private static final int NUMBER_THREADS = 5; 	//Maximum number of threads
	
	private ExecutorService service;				//Service's executor
	
	/**
	 * BackUpPool's constructor
	 */
	public BackUpPool() {
		service = Executors.newFixedThreadPool(NUMBER_THREADS);
	}
	
	/**
	 * Shuts the service down
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
	 * Starts new thread
	 * @param worker This is used by any class whose instances are intended to be executed by a thread
	 */
	@Override
	public void startNewThread(Runnable worker) {
		service.execute(worker);
	}

	/**
	 * Starts the backup's thread
	 * @param sender Channel to be used when we start a new thread to do the backup
	 */
	public void startBackupThread(ChannelSender sender) {		
		startNewThread(sender);
	}
}
