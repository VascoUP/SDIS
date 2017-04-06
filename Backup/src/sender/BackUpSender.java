package sender;

import java.io.IOException;

import connection.ConnectionConstants;
import information.Chunk;
import information.FileInfo;
import information.MessagesHashmap;
import information.PeerInfo;
import message.InfoToMessage;
import message.MessageInfoPutChunk;
import message.MessageInfoStored;

public class BackUpSender extends ChannelSender {
	private String filePath;
	
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
		return MessagesHashmap.getValue(InfoToMessage.toMessage(m));
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
		FileInfo.addBackedUpChunk(new Chunk(filePath, backupMessage.getFileID(), backupMessage.getChunkID()));
	}
	
	public boolean condition() {
		MessageInfoPutChunk backupMessage = (MessageInfoPutChunk) message;
		return getMessages() >= backupMessage.getReplicationDegree();
	}
	
	@Override
	public void execute() {
		do {
			sendMessage();
			cooldown(1000);
		} while( !condition() );

		fileAdd();
		removeMessages();
	}
}
