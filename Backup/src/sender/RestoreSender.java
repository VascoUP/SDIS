package sender;

import java.io.IOException;

import connection.ConnectionConstants;
import information.Chunk;
import information.MessagesHashmap;
import information.PeerInfo;
import message.BasicMessage;
import message.InfoToMessage;
import message.MessageInfoChunk;
import message.MessageInfoGetChunk;
import service.restore.Restore;

public class RestoreSender extends ChannelSender {
	private Restore restoreObject;
	
	public RestoreSender(Restore restoreObject, MessageInfoGetChunk message) throws IOException {
		super( message, ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
		this.restoreObject = restoreObject;
		System.out.println("RestoreSender: constructor");
	}
	
	private int getMessages() {
		MessageInfoGetChunk restoreMessage = (MessageInfoGetChunk) message;
		MessageInfoChunk m = new MessageInfoChunk(
								PeerInfo.peerInfo.getVersionProtocol(), 
								PeerInfo.peerInfo.getServerID(), 
								restoreMessage.getFileID(), 
								restoreMessage.getChunkID(),
								null);
		return MessagesHashmap.getValue(InfoToMessage.toMessage(m));
	}
	
	private void removeMessages() {
		MessageInfoGetChunk restoreMessage = (MessageInfoGetChunk) message;
		MessageInfoChunk m = new MessageInfoChunk(
								PeerInfo.peerInfo.getVersionProtocol(), 
								PeerInfo.peerInfo.getServerID(), 
								restoreMessage.getFileID(), 
								restoreMessage.getChunkID(),
								null);
		MessagesHashmap.removeKey(InfoToMessage.toMessage(m));;
	}
	
	private void signalRestore() {
		MessageInfoGetChunk restoreMessage = (MessageInfoGetChunk) message;
		MessageInfoChunk m = new MessageInfoChunk(
								PeerInfo.peerInfo.getVersionProtocol(), 
								PeerInfo.peerInfo.getServerID(), 
								restoreMessage.getFileID(), 
								restoreMessage.getChunkID(),
								null);
		BasicMessage receivedMessage = MessagesHashmap.searchKey(InfoToMessage.toMessage(m));
		if( receivedMessage == null ) {
			System.out.println("null receivedMessage");
			return ;
		}
		
		Chunk chunk = new Chunk(null, 
				restoreMessage.getFileID(), 
				restoreMessage.getChunkID(), 
				receivedMessage.getBody());
		restoreObject.addReceivedChunk(chunk);
	}
	
	public boolean condition() {
		return getMessages() >= 1;
	}
	
	@Override
	public void execute() {
		do {
			sendMessage();
			System.out.println("RestoreSender: Sent message");
			cooldown(1000);
		} while( !condition() );

		signalRestore();
		removeMessages();
		System.out.println("RestoreSender: Yey");
	}
}