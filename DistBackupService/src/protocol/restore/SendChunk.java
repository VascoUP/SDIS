package protocol.restore;

import java.io.IOException;
import java.net.DatagramPacket;

import connection.ConnectionConstants;
import connection.ReceivingSocekt;
import connection.SendingSocket;
import message.restore.ChunkMessage;
import protocol.Answer;
import protocol.Protocol;
import ui.App;

public class SendChunk extends Protocol implements Answer {

	private ReceivingSocekt mc;
	private SendingSocket mdr;
	
	private ChunkMessage message;
	
	public SendChunk() throws IOException {
		super();
		
		mc = new ReceivingSocekt(ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT);
		mdr = new SendingSocket(ConnectionConstants.MDR_GROUP, ConnectionConstants.MDR_GROUP_PORT);

		message = new ChunkMessage(
				/*version*/	App.getVersionProtocol(), 
				/*senderId*/App.getServerId(), 
				/*fileId*/	1, 
				/*chunkId*/	1);
	}

	public void close() throws IOException {
		this.mdr.leave();
		this.mc.leave();
	}
	
	@Override
	public void send() throws IOException {
		mdr.send("" + message);
	}

	@Override
	public String receive() throws IOException {
		DatagramPacket packet = mc.receive();
		return new String(packet.getData());
	}
}
