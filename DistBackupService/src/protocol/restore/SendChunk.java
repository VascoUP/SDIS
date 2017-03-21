package protocol.restore;

import java.io.IOException;
import java.net.DatagramPacket;

import connection.ConnectionConstants;
import connection.ReceivingSocekt;
import connection.SendingSocket;
import message.backup.StoredMessage;
import protocol.Answer;
import protocol.Protocol;

public class SendChunk extends Protocol implements Answer {

	private ReceivingSocekt mdr;
	private SendingSocket mc;
	
	private StoredMessage message;
	
	public SendChunk() throws IOException {
		super();
		
		mdr = new ReceivingSocekt(ConnectionConstants.MDR_GROUP, ConnectionConstants.MDR_GROUP_PORT);
		mc = new SendingSocket(ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT);
		message = new StoredMessage(
				/*version*/	"1.0", 
				/*senderId*/1, 
				/*fileId*/	1, 
				/*chunkId*/	1);
	}

	public void close() throws IOException {
		this.mdr.leave();
		this.mc.leave();
	}
	
	@Override
	public void send() throws IOException {
		mc.send("" + message);
	}

	@Override
	public String receive() throws IOException {
		DatagramPacket packet = mdr.receive();
		return new String(packet.getData());
	}
}
