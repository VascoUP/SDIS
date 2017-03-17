package connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class SendingSocket extends SLMCast {

	public SendingSocket(String addr, int port) throws IOException {
		super(addr, port);

		mcast_socket = new MulticastSocket();
	}
	
    public void send(String message) throws IOException {
    	byte[] m = message.getBytes();
    	DatagramPacket packet = new DatagramPacket(
    			m,
    			m.length,
				mcast_address,
				mcast_port);
    	mcast_socket.send(packet);
    }
    
	public void leave() throws IOException {
    	mcast_socket.close();
    }
}
