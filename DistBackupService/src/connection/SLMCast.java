package connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;

/**
 * Serverless multicast group
 * @author vasco
 *
 */
public class SLMCast {
	
	private final int mcast_port;
	private final MulticastSocket mcast_socket;
	private final InetAddress mcast_address;
	
	
	public SLMCast() throws IOException {
		mcast_port = 2015;
		
		mcast_address = InetAddress.getByName("localhost");
		
		mcast_socket = new MulticastSocket(mcast_port);
		mcast_socket.joinGroup(mcast_address);
	}
	
	
	public void join() throws IOException {
		mcast_socket.joinGroup(mcast_address);
	}
	
    public void leave() throws IOException {
    	mcast_socket.leaveGroup(mcast_address);
    	mcast_socket.close();
    }
    
    
    public void send(DatagramPacket packet) throws IOException {
    	mcast_socket.send(packet);
    }
    
    public DatagramPacket receive() throws SocketTimeoutException, IOException {
    	DatagramPacket packet = new DatagramPacket(
    			new byte[ConnectionConstants.PACKET_SIZE], 
    			ConnectionConstants.PACKET_SIZE);
    	
    	mcast_socket.receive(packet);
    	
		return packet;
    } 
}
