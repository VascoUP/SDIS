package sender;

import java.io.IOException;

import connection.SendingSocket;
import message.MessageInfo;
import message.MessageInfoToByteArray;

public abstract class ChannelSender implements Runnable {

	private SendingSocket socket;
	protected MessageInfo message;
	
	public ChannelSender(MessageInfo message, String addr, int port) throws IOException {
		socket = new SendingSocket(addr, port);
		this.message = message;
	}
	
	public void sendMessage() {
		System.out.println("Channel Sender: sendMessage");
		try {
			socket.send(MessageInfoToByteArray.infoToByteArray(this.message));
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
