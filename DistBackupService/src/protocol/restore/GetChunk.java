package protocol.restore;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import connection.ConnectionConstants;
import connection.ReceivingSocekt;
import connection.SendingSocket;
import message.restore.GetChunkMessage;
import protocol.Protocol;
import protocol.Request;
import ui.App;

public class GetChunk extends Protocol implements Request {

	private SendingSocket mc;
	private ReceivingSocekt mdr;
	
	private GetChunkMessage message;
	
	public GetChunk() throws IOException {
		super();

		mc = new SendingSocket(ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT);
		mdr = new ReceivingSocekt(ConnectionConstants.MDR_GROUP, ConnectionConstants.MDR_GROUP_PORT);

		message = new GetChunkMessage(
				/*version*/	App.getVersionProtocol(), 
				/*senderId*/App.getServerId(), 
				/*fileId*/	1, 
				/*chunkId*/	1);
	}
	
	public void socketTimeout(int t) {
		try {
			mdr.setTimeout(t);
		} catch (SocketException e) {
			System.out.println("Couldn't set a timeout");
		}
	}
	
	public void close() throws IOException  {
		this.mdr.leave();
		this.mc.leave();
	}
	
	@Override
	public void send() throws IOException {
		mc.send("" + message);
	}

	@Override
	public String receive() throws SocketTimeoutException, IOException {
		DatagramPacket packet = mdr.receive();
		return new String(packet.getData());
	}
}
