package sender;

import java.io.IOException;

import connection.SendingSocket;
import message.MessageInfo;
import message.MessageInfoToByteArray;
import message.MessageToString;

public abstract class ChannelSender implements Runnable {

	private SendingSocket socket;
	protected MessageInfo message;
	
	public ChannelSender(MessageInfo message, String addr, int port) throws IOException {
		socket = new SendingSocket(addr, port);
		this.message = message;
	}
	
	public void closeChannel() {
		try {
			socket.leave();
		} catch (IOException e) {
			System.err.println("Error closing sender channel");
		}
	}
	
	protected void cooldown(long ms) {
		try {
			long waitUntilMillis = System.currentTimeMillis() + ms;
			long waitTimeMillis = ms;
			do {
				Thread.sleep(waitTimeMillis);
				waitTimeMillis = waitUntilMillis - System.currentTimeMillis();
			} while (waitTimeMillis > 0);
		} catch (InterruptedException e) {
		}
	}
	
	public void execute() {
		sendMessage();
	}
	
	@Override
	public void run() {
		execute();
	}
	
	public void sendMessage() {
		try {
			socket.send(MessageInfoToByteArray.infoToByteArray(this.message));
		} catch (IOException e) {
			System.err.println("Error send message " + this.message.getMessageType());
		}
	}
	
	
	@Override
	public String toString() {
		return MessageToString.getName(message);
	}
}
