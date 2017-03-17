package protocol.backup;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketTimeoutException;

import connection.ConnectionConstants;
import connection.ReceivingSocekt;
import connection.SLMCast;
import connection.SendingSocket;
import message.Message;
import message.backup.StoredChunkMessage;
import protocol.Answer;
import protocol.Protocol;

public class AnswerBackUp extends Protocol implements Answer {

	private SLMCast mdb;
	private Message message;
	
	public AnswerBackUp() throws IOException {
		super();
		
		mdb = new ReceivingSocekt(ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
		mc = new SendingSocket(ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT);
		message = new StoredChunkMessage(
				/*version*/"1.0", 
				/*senderId*/1, 
				/*fileId*/1, 
				/*hunkId*/1);
	}

	
	
	@Override
	public void send() throws IOException {
		System.out.println("Send answer");
		mc.send("hello dankness".getBytes());
	}

	@Override
	public String receive() throws SocketTimeoutException, IOException {
		System.out.println("Receive request");
		DatagramPacket packet = mdb.receive();
		return packet.toString();
	}
}
