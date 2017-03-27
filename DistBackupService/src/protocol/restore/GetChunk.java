package protocol.restore;

import java.io.IOException;

import connection.ConnectionConstants;
import message.restore.GetChunkMessage;
import protocol.general.Protocol;
import ui.App;

public class GetChunk extends Protocol {
	public GetChunk() throws IOException {
		super(	ConnectionConstants.MDR_GROUP, ConnectionConstants.MDR_GROUP_PORT, 
				ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT);
	}
	
	public void setMessage(String fileId, int chunkId) {
		message = new GetChunkMessage(
				/*version*/	App.getVersionProtocol(),
				/*senderId*/App.getServerId(), 
				/*fileId*/	fileId, 
				/*chunkId*/	chunkId);
	}
}
