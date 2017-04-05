package threads;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import message.MessageInfoGetChunk;
import sender.RestoreSender;

public class RestorePool implements ThreadPool {
	private static final int NUMBER_THREADS = 5;
	
	private ExecutorService service;
	
	public RestorePool() {
		service = Executors.newFixedThreadPool(NUMBER_THREADS);
	}
	
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

	@Override
	public void startNewThread(Runnable worker) {
		service.execute(worker);
	}

	public void startRestoreThread(MessageInfoGetChunk message) {
		RestoreSender restore = null;
		try {
			restore = new RestoreSender(message);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		startNewThread(restore);
	}
}
