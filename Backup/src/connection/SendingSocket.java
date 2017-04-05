package connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class SendingSocket extends SLMCast {

	public SendingSocket() throws IOException {
		super( ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT );

		mcast_socket = new MulticastSocket();
	}

    @Override
	public void leave() throws IOException {
    	mcast_socket.close();
    }
    
	public void send(byte[] message) throws IOException {
    	DatagramPacket packet = new DatagramPacket(
    			message,
    			message.length,
				mcast_address,
				mcast_port);
    	mcast_socket.send(packet);
    }
}
