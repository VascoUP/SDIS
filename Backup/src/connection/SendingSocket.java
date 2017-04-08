package connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

/**
 * 
 * This class builds the socket which will sent the datagram packet
 *
 */
public class SendingSocket extends SLMCast {

	/**
	 * SendingSocket's constructor
	 * @param addr Socket's address
	 * @param port Socket's port
	 * @throws IOException This class builds the socket which will sent the datagram packet
	 */
	public SendingSocket(String addr, int port) throws IOException {
		super(addr, port);

		mcast_socket = new MulticastSocket(); //Creates a multicast socket
	}
	

	/**
	 * Verifies if the multicast socket is closed
	 * @return true if it is closed, false otherwise
	 */
	public boolean isClosed() {
		return mcast_socket.isClosed();
	}
	
	/**
	 * Closes the multicast socket
	 */
    @Override
	public void leave() throws IOException {
    	mcast_socket.close();
    }
    
    /**
     * Sends the datagram packet by the multicast socket
     * @param message Message that will be sent
     * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
     */
	public void send(byte[] message) throws IOException {
		//Creates the datagram packet that will be sent by the multicast socket
		DatagramPacket packet = new DatagramPacket(
    			message,
    			message.length,
				mcast_address,
				mcast_port); 
    	mcast_socket.send(packet);
    }
}
