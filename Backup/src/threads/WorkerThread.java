package threads;

import service.general.ServiceParser;

class WorkerThread implements Runnable {
	private final int id;
	
	public WorkerThread(int id) {
		this.id = id;
	}
	
	@Override
	public void run() {
		Thread.currentThread().setName("" + id);
		while( !Thread.currentThread().isInterrupted() )
			ServiceParser.parseService();
	}
}
