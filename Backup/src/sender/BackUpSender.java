package sender;

import java.io.IOException;

import connection.ConnectionConstants;
import information.ChunkBackedUp;
import information.FileInfo;
import information.MessagesHashmap;
import information.PeerInfo;
import message.InfoToMessage;
import message.MessageInfoPutChunk;
import message.MessageInfoStored;

public class BackUpSender extends ChannelSender {
	private static final int MAX_NUMBER_TRIES = 3;
	private final String filePath;
	private int prepdeg = -1;
	
	public BackUpSender(String filePath, MessageInfoPutChunk message) throws IOException {
		super( message, ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
		this.filePath = filePath;
	}
	
	private int getMessages() {		
		MessageInfoPutChunk backupMessage = (MessageInfoPutChunk) message;
		MessageInfoStored m = new MessageInfoStored(
				PeerInfo.peerInfo.getVersionProtocol(), 
				PeerInfo.peerInfo.getServerID(), 
				backupMessage.getFileID(), 
				backupMessage.getChunkID());
		prepdeg = MessagesHashmap.getSize(InfoToMessage.toMessage(m));
		return prepdeg;
	}
	
	private void removeMessages() {
		MessageInfoPutChunk backupMessage = (MessageInfoPutChunk) message;
		MessageInfoStored m = new MessageInfoStored(
									PeerInfo.peerInfo.getVersionProtocol(), 
									PeerInfo.peerInfo.getServerID(), 
									backupMessage.getFileID(), 
									backupMessage.getChunkID());
		MessagesHashmap.removeKey(InfoToMessage.toMessage(m));;
	}
	
	private void fileAdd() {
		MessageInfoPutChunk backupMessage = (MessageInfoPutChunk) message;
		FileInfo.addBackedUpChunk(
				new ChunkBackedUp(
						filePath, 
						backupMessage.getFileID(), 
						backupMessage.getChunkID(),
						backupMessage.getReplicationDegree(),
						prepdeg));
	}
	
	public boolean condition() {
		MessageInfoPutChunk backupMessage = (MessageInfoPutChunk) message;
		return getMessages() >= backupMessage.getReplicationDegree();
	}
	
	@Override
	public void execute() {
		int nTries = 0;
		
		do {
			sendMessage();
			cooldown(1000);			
		} while( !condition() && ++nTries < MAX_NUMBER_TRIES );
		
		if( nTries >= MAX_NUMBER_TRIES ) {
			removeMessages();
			return ;
		}
		
		fileAdd();
		removeMessages();
	}
}
