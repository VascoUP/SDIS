package connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;

public class ReceivingSocekt extends SLMCast {

	public ReceivingSocekt(String addr, int port) throws IOException {
		super(addr, port);

		mcast_socket = new MulticastSocket(mcast_port);
		mcast_socket.joinGroup(mcast_address);
	}
	
    public DatagramPacket receive() throws SocketTimeoutException, IOException {
    	DatagramPacket packet = new DatagramPacket(
    			new byte[ConnectionConstants.PACKET_SIZE], 
    			ConnectionConstants.PACKET_SIZE);
    	
    	mcast_socket.receive(packet);
    	
		return packet;
    }
}
