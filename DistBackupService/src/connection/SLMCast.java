package connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;

import message.Message;

/**
 * Serverless multicast group
 * @author vasco
 *
 */
public class SLMCast {
	
	private final int mcast_port;
	private final MulticastSocket mcast_socket;
	private final InetAddress mcast_address;
	
	public SLMCast(String addr, int port) throws IOException {
		mcast_port = port;
		
		mcast_address = InetAddress.getByName(addr);
		
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
    
    
    public void send(Message message) throws IOException {
    	DatagramPacket packet = new DatagramPacket(
    			message.getMessage(),
    			message.getOffset(),
				message.getLength(),
				mcast_address,
				mcast_port);
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
