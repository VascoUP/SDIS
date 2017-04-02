package threads;

public class WorkerThread implements Runnable {

	private String message;
	
	public WorkerThread(String message) {
		this.message = message;
	}
	
	@Override
	public void run() {
		Thread.currentThread().setName("Thread " + message);
		System.out.println(Thread.currentThread().getName()+" (Start) message = "+ message);  
		processmessage();
		System.out.println(Thread.currentThread().getName()+" (End)");
	}
	
	public void processmessage() {
		try {  
			Thread.sleep(2000);  
		} catch (InterruptedException e) { 
			e.printStackTrace(); 
		}
	}

}
