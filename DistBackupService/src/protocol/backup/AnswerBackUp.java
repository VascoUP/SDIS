package protocol.backup;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketTimeoutException;

import connection.ConnectionConstants;
import connection.ReceivingSocekt;
import connection.SendingSocket;
import message.Message;
import message.backup.StoredChunkMessage;
import protocol.Answer;
import protocol.Protocol;

public class AnswerBackUp extends Protocol implements Answer {

	private ReceivingSocekt mdb;
	private SendingSocket mc;
	
	private Message message;
	
	public AnswerBackUp() throws IOException {
		super();
		
		mdb = new ReceivingSocekt(ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
		mc = new SendingSocket(ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT);
		message = new StoredChunkMessage(
				/*version*/	"1.0", 
				/*senderId*/1, 
				/*fileId*/	1, 
				/*chunkId*/	1);
	}

	
	
	@Override
	public void send() throws IOException {
		mc.send("" + message);
	}

	@Override
	public String receive() throws SocketTimeoutException, IOException {
		DatagramPacket packet = mdb.receive();
		return new String(packet.getData());
	}
}
