package threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WorkerPool {
	private ExecutorService service;
	private int nThreads = 0;
	
	public WorkerPool() {
		service = Executors.newFixedThreadPool(5);
	}

	public void startAllWorkerThreads() {
		for( int i = 0; i < 10; i++ ) {
			nThreads++;
			startNewWorkerThread();
		}
	}
	
	public void startNewWorkerThread() {
		Runnable worker = new WorkerThread("" + nThreads);  
		service.execute(worker);		
	}
	
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
}
