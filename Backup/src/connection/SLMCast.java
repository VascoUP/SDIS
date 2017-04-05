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
	
	final int mcast_port;
	final InetAddress mcast_address;
	MulticastSocket mcast_socket;
	
	SLMCast(String addr, int port) throws IOException {
		mcast_port = port;
		mcast_address = InetAddress.getByName(addr);
	}

	public void leave() throws IOException {
    	mcast_socket.leaveGroup(mcast_address);
    	mcast_socket.close();
    }

}
