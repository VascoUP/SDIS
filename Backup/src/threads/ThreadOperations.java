package threads;

public interface ThreadOperations {
	public void close() throws InterruptedException;
	public String getName();
	public void interrupt() throws InterruptedException;
	public boolean isAlive();
	public void join() throws InterruptedException;
	public void start();
}
