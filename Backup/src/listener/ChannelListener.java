package listener;

import java.io.IOException;
import java.net.DatagramPacket;

import connection.ReceivingSocekt;
import information.MessageQueue;

/**
 * 
 * This class builds a channel listener, that implements the Runnable interface
 *
 */
public abstract class ChannelListener implements Runnable {

	private ReceivingSocekt socket;		//Receiving socket
	
	/**
	 * ChannelListener's constructor
	 * @param addr Socket's address
	 * @param port Socket's port
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	public ChannelListener(String addr, int port) throws IOException {
		socket = new ReceivingSocekt(addr, port);
	}
	
	/**
	 * Closes the channel
	 */
	public void closeChannel() {
		try {
			socket.leave();
		} catch (IOException e) {
			System.err.println("Error closing receiving socket");
		}
	}
	
	/**
	 * Puts the message into the MessageQueue
	 * @param message Message that will be added
	 */
	private void queueInMessage(byte[] message) {
		MessageQueue.put(message);
	}
	
	/**
	 * Receives the message's content and puts the message into the MessageQueue
	 */
	private void receiveMessage() {
		byte[] data = receiver();
		if( data != null )
			queueInMessage(data);
	}
	
	/**
	 * Receives the datagram packet from the socket
	 * @return The content of the datagram packet received
	 */
	private byte[] receiver() {
		DatagramPacket packet;
		
		try {
			packet = socket.receive();
		} catch (Exception e) {
			return null;
		}
		
		return packet.getData();
	}
	
	@Override
	/**
	 * Runs the received message while the thread isn't interrupted
	 */
	public void run() {
		while( !Thread.interrupted() )
			receiveMessage();
	}

}
