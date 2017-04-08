package connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;

/**
 * 
 * Class that builds the socket that will be receiving the datagram packet
 *
 */
public class ReceivingSocekt extends SLMCast {

	/**
	 * ReceivingSocket's constructor
	 * @param addr Socket's address
	 * @param port Socket's port
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	public ReceivingSocekt(String addr, int port) throws IOException {
		super(addr, port);

		mcast_socket = new MulticastSocket(mcast_port); //Creates a multicast socket
		mcast_socket.joinGroup(mcast_address); //Joins to the multicast group
	}
	
	/**
	 * Verifies if the multicast socket is closed
	 * @return true if it is closed, false otherwise
	 */
	public boolean isClosed() {
		return mcast_socket.isClosed();
	}
	
	/**
	 * Receives the datagram packet from the multicast socket
	 * @return The datagram packet received
	 * @throws SocketTimeoutException Signals that a timeout has occurred on a socket read or accept
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
    public DatagramPacket receive() throws SocketTimeoutException, IOException {
    	DatagramPacket packet = new DatagramPacket(
    			new byte[ConnectionConstants.PACKET_SIZE_OVERHEAD], 
    			ConnectionConstants.PACKET_SIZE_OVERHEAD); //Creates the datagram packet where will be saved the packet received
    	
    	mcast_socket.receive(packet);
    	
		return packet;
    }
}
