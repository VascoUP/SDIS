package threads;

public interface ThreadPool {
	public void shutdown();
	public void startNewThread(Runnable worker);
}
