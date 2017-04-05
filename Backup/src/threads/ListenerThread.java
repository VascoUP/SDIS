package threads;

import listener.ChannelListener;

class ListenerThread implements ThreadOperations {
	private final Thread thread;
	private final ChannelListener listener;
	
	public ListenerThread(Thread thread, ChannelListener listener) {
		this.thread = thread;
		this.listener = listener;
	}
	
	public void close() {
		interrupt();
		listener.closeChannel();
	}

	private void interrupt() {
		thread.interrupt();
		
	}

	public void start() {
		thread.start();
	}
}

