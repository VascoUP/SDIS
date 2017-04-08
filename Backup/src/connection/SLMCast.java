package connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

/**
 * 
 * This class builds a serverless multicast group
 *
 */
public abstract class SLMCast {
	
	protected final int mcast_port; 			//Multicast's port
	protected final InetAddress mcast_address;	//Multicast's address 
	protected MulticastSocket mcast_socket;     //Multicast's socket
	
	/**
	 * Serverless multicast group's constructor
	 * @param addr Multicast's address
	 * @param port Multicast's port
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	public SLMCast(String addr, int port) throws IOException {
		mcast_port = port;
		mcast_address = InetAddress.getByName(addr); //Creates the InetAddress needed to initiates the multicast's address
	}
	
	/**
	 * Joins the multicast's socket to the multicast's group
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	public void join() throws IOException {
		mcast_socket.joinGroup(mcast_address);
	}
	
	/**
	 * Leaves the multicast's group and closes the multicast's socket
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	public void leave() throws IOException {
    	mcast_socket.leaveGroup(mcast_address);
    	mcast_socket.close();
    }
	
	/**
	 * Sets the socket's time out
	 * @param timeOut Value to sets the time out
	 * @throws SocketException Thrown to indicate that there is an error creating or accessing a Socket
	 */
    public void setTimeout(int timeOut) throws SocketException {
		mcast_socket.setSoTimeout(timeOut);
	}
}
