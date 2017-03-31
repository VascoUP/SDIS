package protocol.backup;

import java.io.IOException;

import connection.ConnectionConstants;
import information.PeerInfo;
import message.backup.StoredMessage;
import protocol.general.Protocol;

public class AnswerBackUp extends Protocol {
		
	public AnswerBackUp() throws IOException {
		super(	ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT,
				ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT);
	}
	
	public void setMessage(String fileId, int chunkId) {
		message = new StoredMessage(
				/*version*/	PeerInfo.peerInfo.getVersionProtocol(),
				/*senderId*/PeerInfo.peerInfo.getServerID(), 
				/*fileId*/	fileId, 
				/*chunkId*/	chunkId);
	}
}
