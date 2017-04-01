package threads;

import listener.ChannelListener;

public class ListenerThread implements ThreadOperations {
	private Thread thread;
	private ChannelListener listener;
	
	public ListenerThread(Thread thread, ChannelListener listener) {
		this.thread = thread;
		this.listener = listener;
	}
	
	@Override
	public void start() {
		thread.start();
	}

	@Override
	public boolean isAlive() {
		return thread.isAlive();
	}

	@Override
	public void join() throws InterruptedException {
		throw new InterruptedException("Listener threads cannot be joined");
	}

	@Override
	public void interrupt() throws InterruptedException {
		thread.interrupt();
		
	}

	@Override
	public void close() throws InterruptedException {
		interrupt();
		listener.closeChannel();
	}
}

