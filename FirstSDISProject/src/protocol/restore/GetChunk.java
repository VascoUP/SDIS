package protocol.restore;

import java.io.IOException;

import connection.ConnectionConstants;
import information.PeerInfo;
import message.restore.GetChunkMessage;
import protocol.general.Protocol;

public class GetChunk extends Protocol {
	public GetChunk() throws IOException {
		super(	ConnectionConstants.MDR_GROUP, ConnectionConstants.MDR_GROUP_PORT, 
				ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT);
	}
	
	public void setMessage(String fileId, int chunkId) {
		message = new GetChunkMessage(
				/*version*/	PeerInfo.peerInfo.getVersionProtocol(),
				/*senderId*/PeerInfo.peerInfo.getServerID(), 
				/*fileId*/	fileId, 
				/*chunkId*/	chunkId);
	}
}
