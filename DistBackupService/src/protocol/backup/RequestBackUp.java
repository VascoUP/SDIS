package protocol.backup;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketTimeoutException;
import connection.SLMCast;
import message.Message;
import message.backup.StoreChunkMessage;
import protocol.Protocol;
import protocol.Request;

public class RequestBackUp extends Protocol implements Request {

	private SLMCast mdb;
	private Message message;
	
	public RequestBackUp(String addr_mc, int port_mc, String addr_mdb, int port_mdb) throws IOException {
		super(addr_mc, port_mc);
		
		mdb = new SLMCast(addr_mdb, port_mdb);
		message = new StoreChunkMessage(
				/*version*/"1.0", 
				/*senderId*/1, 
				/*fileId*/1, 
				/*hunkId*/1, 
				/*body*/"body");
	}
	
	
	
	@Override
	public void send() throws IOException {
		System.out.println("Send answer");
		mdb.send(message);
	}

	@Override
	public String receive() throws SocketTimeoutException, IOException {
		System.out.println("Receive request");
		DatagramPacket packet = mc.receive();
		return packet.toString();
	}
}
