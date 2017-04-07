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
	private String filePath;
	private int prepdeg;
	
	public BackUpSender(String filePath, MessageInfoPutChunk message) throws IOException {
		super( message, ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
		this.filePath = filePath;
		this.prepdeg = -1;
		System.out.println("BackUpSender");
	}
	
	private int getValue() {
		if( this.prepdeg == -1 ) {
			MessageInfoPutChunk backupMessage = (MessageInfoPutChunk) message;
			MessageInfoStored m = new MessageInfoStored(
										PeerInfo.peerInfo.getVersionProtocol(), 
										PeerInfo.peerInfo.getServerID(), 
										backupMessage.getFileID(), 
										backupMessage.getChunkID());
			prepdeg = MessagesHashmap.getValue(InfoToMessage.toMessage(m));
		}
		
		return prepdeg;
	}
	
	private int getMessages() {
		return getValue();
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
						getValue()));
	}
	
	public boolean condition() {
		MessageInfoPutChunk backupMessage = (MessageInfoPutChunk) message;
		return getMessages() >= backupMessage.getReplicationDegree();
	}
	
	@Override
	public void execute() {
		do {
			sendMessage();
			System.out.println("BackUpSender: sent message");
			cooldown(1000);
		} while( !condition() );

		fileAdd();
		removeMessages();
	}
}
