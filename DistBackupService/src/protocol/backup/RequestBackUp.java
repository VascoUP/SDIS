package protocol.backup;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketTimeoutException;

import connection.ConnectionConstants;
import connection.ReceivingSocekt;
import connection.SendingSocket;
import message.Message;
import message.backup.StoreChunkMessage;
import protocol.Protocol;
import protocol.Request;

public class RequestBackUp extends Protocol implements Request {

	private SendingSocket mdb;
	private ReceivingSocekt mc;
	
	private Message message;
	
	public RequestBackUp() throws IOException {
		super();
		
		mdb = new SendingSocket(ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
		mc = new ReceivingSocekt(ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT);
		message = new StoreChunkMessage(
				/*version*/"1.0", 
				/*senderId*/1, 
				/*fileId*/1, 
				/*hunkId*/1, 
				/*body*/"SHI WHY AT SHINE");
	}
	
	
	
	@Override
	public void send() throws IOException {
		mdb.send("" + message);
	}

	@Override
	public String receive() throws SocketTimeoutException, IOException {
		DatagramPacket packet = mc.receive();
		return new String(packet.getData());
	}
}
