package listener;

import java.io.IOException;
import java.net.DatagramPacket;

import connection.ReceivingSocekt;
import information.MessageQueue;

public abstract class ChannelListener implements Runnable {

	private ReceivingSocekt socket;
	
	public ChannelListener(String addr, int port) throws IOException {
		socket = new ReceivingSocekt(addr, port);
	}
	

	private byte[] receiver() {
		DatagramPacket packet;
		
		try {
			packet = socket.receive();
		} catch (Exception e) {
			return null;
		}
		
		return packet.getData();
	}
	
	private void queueInMessage(byte[] message) {
		MessageQueue.put(message);
	}
	
	
	private void receiveMessage() {
		byte[] data = receiver();
		if( data != null )
			queueInMessage(data);
	}
	
	
	@Override
	public void run() {
		while( !Thread.interrupted() )
			receiveMessage();
	}
	
	public void closeChannel() {
		try {
			socket.leave();
		} catch (IOException e) {
			System.err.println("Error closing receiving socket");
		}
	}

}
