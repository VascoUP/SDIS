package sender;

import java.io.IOException;

import connection.ConnectionConstants;
import information.Chunk;
import information.FileInfo;
import information.MessagesHashmap;
import information.PeerInfo;
import message.MessageInfoPutChunk;
import message.MessageInfoStored;
import message.MessageToString;

public class BackUpSender extends ChannelSender {
	private String filePath;
	
	public BackUpSender(String filePath, MessageInfoPutChunk message) throws IOException {
		super( message, ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
		this.filePath = filePath;
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
	
	@Override
	public void execute() {
		do {
			sendMessage();
			cooldown(1000);
		} while( !condition() );
		
		MessagesHashmap.removeKey(MessageToString.getName(message));
		MessageInfoPutChunk backupMessage = (MessageInfoPutChunk) message;
		FileInfo.addBackedUpChunk(new Chunk(filePath, backupMessage.getFileID(), backupMessage.getChunkID()));
		System.out.println("BackUpSender: Yey");
	}
}
