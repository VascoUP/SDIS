package threads;

public interface ThreadOperations {
	public void start();
	public boolean isAlive();
	public void join() throws InterruptedException;
	public void interrupt() throws InterruptedException;
	public void close() throws InterruptedException;
	public String getName();
}
