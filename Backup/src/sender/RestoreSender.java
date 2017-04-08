package sender;

import java.io.IOException;

import connection.ConnectionConstants;
import information.ChunkStored;
import information.MessagesHashmap;
import information.PeerInfo;
import message.BasicMessage;
import message.InfoToMessage;
import message.MessageInfoChunk;
import message.MessageInfoGetChunk;
import protocol.Restore;

public class RestoreSender extends ChannelSender {
	private static final int MAX_NUMBER_TRIES = 3;
	private final Restore restoreObject;
	private int prepdeg = -1;
	
	public RestoreSender(Restore restoreObject, MessageInfoGetChunk message) throws IOException {
		super( message, ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
		this.restoreObject = restoreObject;
	}
	
	private int getMessages() {
		MessageInfoGetChunk restoreMessage = (MessageInfoGetChunk) message;
		MessageInfoChunk m = new MessageInfoChunk(
								PeerInfo.peerInfo.getVersionProtocol(), 
								PeerInfo.peerInfo.getServerID(), 
								restoreMessage.getFileID(), 
								restoreMessage.getChunkID(),
								null);
		prepdeg = MessagesHashmap.getSize(InfoToMessage.toMessage(m));
		System.out.println("RestoreSender: prepdeg " + prepdeg);
		return prepdeg;
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
		
		ChunkStored chunk = new ChunkStored (null, 
				restoreMessage.getFileID(), 
				restoreMessage.getChunkID(), 
				getMessages(),
				receivedMessage.getBody());
		restoreObject.addReceivedChunk(chunk);
	}
	
	public boolean condition() {
		return getMessages() >= 1;
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
		
		signalRestore();
		removeMessages();
	}
}