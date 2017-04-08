package sender;

import java.io.IOException;

import connection.SendingSocket;
import message.MessageInfo;
import message.MessageInfoToByteArray;
import message.MessageToString;

/**
 * 
 * This class builds a channel sender that implements the Runnable interface
 *
 */
public abstract class ChannelSender implements Runnable {

	private SendingSocket socket;	//Sending socket
	protected MessageInfo message;	//Message's information
	
	/**
	 * ChannelSender's constructor
	 * @param message Message's information
	 * @param addr Socket's address
	 * @param port Socket's port
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	public ChannelSender(MessageInfo message, String addr, int port) throws IOException {
		socket = new SendingSocket(addr, port);
		this.message = message;
	}
	
	/**
	 * Closes the channel, closing the multicast socket
	 */
	public void closeChannel() {
		try {
			socket.leave();
		} catch (IOException e) {
			System.err.println("Error closing sender channel");
		}
	}
	
	/**
	 * This function makes the thread sleep/waits
	 * @param ms Milliseconds used to calculate the time to put asleep the thread
	 */
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
	
	/**
	 * Sends the messages
	 */
	public void execute() {
		sendMessage();
	}
	
	/**
	 * Executes the sending messages
	 */
	@Override
	public void run() {
		execute();
	}
	
	/**
	 * Sends the datagram packet by the multicast socket
	 */
	public void sendMessage() {
		try {
			socket.send(MessageInfoToByteArray.infoToByteArray(this.message));
		} catch (IOException e) {
			System.err.println("Error send message " + this.message.getMessageType());
		}
	}
	
	/**
	 * Gets the message converted into a string
	 */
	@Override
	public String toString() {
		return MessageToString.getName(message);
	}
}
