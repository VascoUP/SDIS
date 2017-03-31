package connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class SendingSocket extends SLMCast {

	public SendingSocket(String addr, int port) throws IOException {
		super(addr, port);

		mcast_socket = new MulticastSocket();
	}
	
	public boolean isClosed() {
		return mcast_socket.isClosed();
	}
	
    public void send(byte[] message) throws IOException {
    	DatagramPacket packet = new DatagramPacket(
    			message,
    			message.length,
				mcast_address,
				mcast_port);
    	mcast_socket.send(packet);
    }
    
	public void leave() throws IOException {
    	mcast_socket.close();
    }
}
