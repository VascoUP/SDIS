package protocol.backup;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketTimeoutException;
import connection.SLMCast;
import message.Message;
import message.backup.StoredChunkMessage;
import protocol.Answer;
import protocol.Protocol;

public class AnswerBackUp extends Protocol implements Answer {

	private SLMCast mdb;
	private Message message;
	
	public AnswerBackUp(String addr_mc, int port_mc, String addr_mdb, int port_mdb) throws IOException {
		super(addr_mc, port_mc);
		
		mdb = new SLMCast(addr_mdb, port_mdb);
		message = new StoredChunkMessage(
				/*version*/"1.0", 
				/*senderId*/1, 
				/*fileId*/1, 
				/*hunkId*/1);
	}

	
	
	@Override
	public void send() throws IOException {
		System.out.println("Send answer");
		mc.send(message);
	}

	@Override
	public String receive() throws SocketTimeoutException, IOException {
		System.out.println("Receive request");
		DatagramPacket packet = mdb.receive();
		return packet.toString();
	}
}
