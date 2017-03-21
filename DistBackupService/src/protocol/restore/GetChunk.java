package protocol.restore;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import connection.ConnectionConstants;
import connection.ReceivingSocekt;
import connection.SendingSocket;
import message.backup.BackUpMessage;
import protocol.Protocol;
import protocol.Request;

public class GetChunk extends Protocol implements Request {

	private SendingSocket mdb;
	private ReceivingSocekt mc;
	
	private BackUpMessage message;
	
	public GetChunk() throws IOException {
		super();
		
		mdb = new SendingSocket(ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
		mc = new ReceivingSocekt(ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT);
		message = new BackUpMessage(
				/*version*/	"1.0", 
				/*senderId*/1, 
				/*fileId*/	1, 
				/*chunkId*/	1, 
				/*body*/	"SHI WHY AT SHINE".getBytes());
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
	
	@Override
	public void send() throws IOException {
		mdb.send("" + message);
	}

	@Override
	public String receive() throws SocketTimeoutException, IOException {
		DatagramPacket packet = mc.receive();
		return new String(packet.getData());
	}
	
	public void setChunk(int id, byte[] data){
		message.setChunkID(id);
		message.setChunkInformation(data);
	}
}
