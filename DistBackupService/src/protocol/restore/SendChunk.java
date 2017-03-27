package protocol.restore;

import java.io.IOException;

import connection.ConnectionConstants;
import message.restore.ChunkMessage;
import protocol.general.Protocol;
import ui.App;

public class SendChunk extends Protocol {
	
	public SendChunk() throws IOException {
		super(	ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT,
				ConnectionConstants.MDR_GROUP, ConnectionConstants.MDR_GROUP_PORT);
	}
	
	public void setMessage(String fileId, int chunkId) {
		message = new ChunkMessage(
				/*version*/	App.getVersionProtocol(),
				/*senderId*/App.getServerId(), 
				/*fileId*/	fileId, 
				/*chunkId*/	chunkId);
	}
}
