package threads;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import message.MessageInfoPutChunk;
import sender.BackUpSender;

public class BackUpPool implements ThreadPool {
	private static final int NUMBER_THREADS = 5;
	
	private ExecutorService service;
	
	public BackUpPool() {
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

	public void startBackupThread(MessageInfoPutChunk message) {
		BackUpSender backup = null;
		try {
			backup = new BackUpSender(message);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		startNewThread(backup);
	}
}
