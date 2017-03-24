package protocol.backup;

import java.io.IOException;

import connection.ConnectionConstants;
import message.backup.BackUpMessage;
import protocol.general.Protocol;
import ui.App;

public class RequestBackUp extends Protocol {
	
	public RequestBackUp() throws IOException {
		super(	ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT,
				ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
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
