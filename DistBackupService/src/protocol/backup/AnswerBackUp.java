package protocol.backup;

import java.io.IOException;
import java.net.DatagramPacket;

import connection.ConnectionConstants;
import connection.ReceivingSocekt;
import connection.SendingSocket;
import message.backup.StoredMessage;
import protocol.Answer;
import protocol.Protocol;

public class AnswerBackUp extends Protocol implements Answer {

	private ReceivingSocekt mdb;
	private SendingSocket mc;
	
	private StoredMessage message;
	
	public AnswerBackUp() throws IOException {
		super();
		
		mdb = new ReceivingSocekt(ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
		mc = new SendingSocket(ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT);
		message = new StoredMessage(
				/*version*/	"1.0", 
				/*senderId*/1, 
				/*fileId*/	1, 
				/*chunkId*/	1);
	}

	public void close() throws IOException {
		this.mdb.leave();
		this.mc.leave();
	}
	
	@Override
	public void send() throws IOException {
		mc.send("" + message);
	}

	@Override
	public String receive() throws IOException {
		DatagramPacket packet = mdb.receive();
		return new String(packet.getData());
	}
}
