package sender;

import java.io.IOException;

import connection.SendingSocket;
import message.general.Message;

public abstract class ChannelSender implements Runnable {

	private SendingSocket socket;
	protected Message message;
	
	public ChannelSender(Message message, String addr, int port) throws IOException {
		socket = new SendingSocket(addr, port);
		this.message = message;
	}
	
	public void sendMessage() {
		System.out.println("Channel Sender: sendMessage");
		try {
			socket.send(this.message.getMessage());
		} catch (IOException e) {
			System.err.println("Error send message " + this.message.getMessageType());
		}
	}
	
	@Override
	public void run() {
		sendMessage();
	}
	
	public void closeChannel() {
		System.out.println("Channel Sender: closeChannel");
		try {
			socket.leave();
		} catch (IOException e) {
			System.err.println("Error closing sender channel");
		}
	}
	
	
	@Override
	public String toString() {
		return "" + message;
	}
}
