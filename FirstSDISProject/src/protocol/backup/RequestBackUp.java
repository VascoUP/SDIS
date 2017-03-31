package protocol.backup;

import java.io.IOException;

import connection.ConnectionConstants;
import information.PeerInfo;
import message.backup.BackUpMessage;
import protocol.general.Protocol;

public class RequestBackUp extends Protocol {
	public RequestBackUp() throws IOException {
		super(	ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT,
				ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
	}

	public void setMessage(String fileId, int chunkId, byte[] data) {
		message = new BackUpMessage(
				/*version*/	PeerInfo.peerInfo.getVersionProtocol(),
				/*senderId*/PeerInfo.peerInfo.getServerID(), 
				/*fileId*/	fileId, 
				/*chunkId*/	chunkId, 
				/*body*/	data);
	}
}
