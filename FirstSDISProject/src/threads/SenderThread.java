package threads;

import sender.ChannelSender;

public class SenderThread implements ThreadOperations{
	private Thread thread;
	private ChannelSender sender;
	
	public SenderThread(Thread thread, ChannelSender sender) {
		this.thread = thread;
		this.sender = sender;
		thread.setName("" + sender);
	}

	public ChannelSender getSender() {
		return sender;
	}
	
	@Override
	public void start() {
		System.out.println(Thread.currentThread().getName());
		thread.start();
	}

	@Override
	public boolean isAlive() {
		return thread.isAlive();
	}

	@Override
	public void join() throws InterruptedException {
		thread.join();
	}
	
	@Override
	public void interrupt() throws InterruptedException {
		throw new InterruptedException("Sender threads cannot be interrupted");
	}

	@Override
	public void close() throws InterruptedException {
		join();
		sender.closeChannel();
	}

	@Override
	public String getName() {
		return thread.getName();
	}
}
