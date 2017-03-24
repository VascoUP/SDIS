package protocol.backup;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import connection.ConnectionConstants;
import connection.ReceivingSocekt;
import connection.SendingSocket;
import message.backup.BackUpMessage;
import ui.App;

public class RequestBackUp {

	private SendingSocket mdb;
	private ReceivingSocekt mc;
	
	private BackUpMessage message;
	
	public RequestBackUp() throws IOException {
		super();
		
		mdb = new SendingSocket(ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
		mc = new ReceivingSocekt(ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT);
	}
	
	public void socketTimeout(int t) {
		try {
			mc.setTimeout(t);
		} catch (SocketException e) {
			System.out.println("Couldn't set a timeout");
		}
	}
	
	public void close() throws IOException  {
		this.mdb.leave();
		this.mc.leave();
	}
	
	public void send() throws IOException {
		mdb.send("" + message);
	}

	public String receive() throws SocketTimeoutException, IOException {
		DatagramPacket packet = mc.receive();
		return new String(packet.getData());
	}
	
	public void setMessage(int fileId, int chunkId, byte[] data) {
		message = new BackUpMessage(
				/*version*/	App.getVersionProtocol(),
				/*senderId*/App.getServerId(), 
				/*fileId*/	fileId, 
				/*chunkId*/	chunkId, 
				/*body*/	data);
	}
}
