package protocol.restore;

import java.io.IOException;

import connection.ConnectionConstants;
import information.PeerInfo;
import message.restore.ChunkMessage;
import protocol.general.Protocol;

public class SendChunk extends Protocol {
	
	public SendChunk() throws IOException {
		super(	ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT,
				ConnectionConstants.MDR_GROUP, ConnectionConstants.MDR_GROUP_PORT);
	}
	
	public void setMessage(String fileId, int chunkId, byte[] data) {
		message = new ChunkMessage(
				/*version*/	PeerInfo.peerInfo.getVersionProtocol(),
				/*senderId*/PeerInfo.peerInfo.getServerID(), 
				/*fileId*/	fileId, 
				/*chunkId*/	chunkId,
				/*chunk*/	data);
	}
}
