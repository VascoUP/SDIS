package connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

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
	
	public void setTimeout(int timeOut) throws SocketException {
		mcast_socket.setSoTimeout(timeOut);
	}
	
	public void join() throws IOException {
		mcast_socket.joinGroup(mcast_address);
	}
	
    public void leave() throws IOException {
    	mcast_socket.leaveGroup(mcast_address);
    	mcast_socket.close();
    }
}