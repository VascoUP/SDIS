package sender;

import java.io.IOException;

import connection.ConnectionConstants;
import information.MessagesHashmap;
import information.PeerInfo;
import message.MessageInfoChunk;
import message.MessageInfoGetChunk;
import message.MessageToString;
import service.restore.Restore;

public class RestoreSender extends ChannelSender {
	private Restore restoreObject;
	
	public RestoreSender(Restore restoreObject, MessageInfoGetChunk message) throws IOException {
		super( message, ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
		this.restoreObject = restoreObject;
	}
	
	public boolean condition() {
		MessageInfoGetChunk restoreMessage = (MessageInfoGetChunk) message;
		MessageInfoChunk m = new MessageInfoChunk(
									PeerInfo.peerInfo.getVersionProtocol(), 
									PeerInfo.peerInfo.getServerID(), 
									restoreMessage.getFileID(), 
									restoreMessage.getChunkID(),
									null);
		String key = MessageToString.getName(m);
		return MessagesHashmap.getValue(key) >= 1;
	}
	
	@Override
	public void execute() {
		do {
			sendMessage();
			cooldown(1000);
		} while( !condition() );
		
		MessagesHashmap.removeKey(MessageToString.getName(message));
		System.out.println("RestoreSender: Yey");
	}
}
