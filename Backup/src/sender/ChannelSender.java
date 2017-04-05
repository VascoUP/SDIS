package sender;

import connection.SendingSocket;
import message.MessageInfo;
import message.MessageInfoToByteArray;
import message.MessageToString;

import java.io.IOException;

public abstract class ChannelSender implements Runnable {

	private SendingSocket socket;
	MessageInfo message;
	
	ChannelSender(MessageInfo message) throws IOException {
		socket = new SendingSocket();
		this.message = message;
	}

    void execute() {
		sendMessage();
	}
	
	@Override
	public void run() {
		execute();
	}
	
	void sendMessage() {
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
