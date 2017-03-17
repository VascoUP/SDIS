package connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Serverless multicast group
 * @author vasco
 *
 */
public abstract class SLMCast {
	
	protected final int mcast_port;
	protected final InetAddress mcast_address;
	protected MulticastSocket mcast_socket;
	
	public SLMCast(String addr, int port) throws IOException {
		mcast_port = port;
		mcast_address = InetAddress.getByName(addr);
	}
	
	public void join() throws IOException {
		mcast_socket.joinGroup(mcast_address);
	}
	
    public void leave() throws IOException {
    	mcast_socket.leaveGroup(mcast_address);
    	mcast_socket.close();
    }
}
