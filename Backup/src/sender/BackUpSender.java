package sender;

import java.io.IOException;

import connection.ConnectionConstants;
import information.MessagesHashmap;
import information.PeerInfo;
import message.MessageInfoPutChunk;
import message.MessageInfoStored;
import message.MessageToString;

public class BackUpSender extends ChannelSender {	
	public BackUpSender(MessageInfoPutChunk message) throws IOException {
		super( message, ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
	}
	
	public boolean condition() {
		MessageInfoPutChunk backupMessage = (MessageInfoPutChunk) message;
		MessageInfoStored m = new MessageInfoStored(
									PeerInfo.peerInfo.getVersionProtocol(), 
									PeerInfo.peerInfo.getServerID(), 
									backupMessage.getFileID(), 
									backupMessage.getChunkID());
		String key = MessageToString.getName(m);
		return MessagesHashmap.getValue(key) >= backupMessage.getReplication_degree();
	}
	
	private void cooldown(long ms) {
		try {
			long waitUntilMillis = System.currentTimeMillis() + ms;
			long waitTimeMillis = ms;
			do {
				Thread.sleep(waitTimeMillis);
				waitTimeMillis = waitUntilMillis - System.currentTimeMillis();
			} while (waitTimeMillis > 0);
		} catch (InterruptedException e) {
		}
	}
	
	@Override
	public void execute() {
		do {
			sendMessage();
			cooldown(1000);
		} while( !condition() );
		
		MessagesHashmap.removeKey(MessageToString.getName(message));
		System.out.println("BackUpSender: Yey");
	}
}
