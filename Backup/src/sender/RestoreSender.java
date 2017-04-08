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
		MessageInfoChunk m1 = new MessageInfoChunk(
								PeerInfo.peerInfo.getVersionProtocol(), 
								PeerInfo.peerInfo.getServerID(), 
								restoreMessage.getFileID(), 
								restoreMessage.getChunkID(),
								new byte[0]);
		BasicMessage m2 = InfoToMessage.toMessage(m1);
		if( m2 != null )
			prepdeg = MessagesHashmap.getSize(m2);
		return prepdeg;
	}
	
	private void removeMessages() {
		MessageInfoGetChunk restoreMessage = (MessageInfoGetChunk) message;
		MessageInfoChunk m1 = new MessageInfoChunk(
								PeerInfo.peerInfo.getVersionProtocol(), 
								PeerInfo.peerInfo.getServerID(), 
								restoreMessage.getFileID(), 
								restoreMessage.getChunkID(),
								new byte[0]);
		BasicMessage m2 = InfoToMessage.toMessage(m1);
		if( m2 != null )
			MessagesHashmap.removeKey(m2);
	}
	
	private void signalRestore() {
		MessageInfoGetChunk restoreMessage = (MessageInfoGetChunk) message;
		MessageInfoChunk m1 = new MessageInfoChunk(
								PeerInfo.peerInfo.getVersionProtocol(), 
								PeerInfo.peerInfo.getServerID(), 
								restoreMessage.getFileID(), 
								restoreMessage.getChunkID(),
								new byte[0]);
		BasicMessage m2 = InfoToMessage.toMessage(m1);
		if( m2 == null )
			return ;
		
		BasicMessage receivedMessage = MessagesHashmap.searchKey(m2);
		if( receivedMessage == null ) {
			System.out.println("null receivedMessage");
			return ;
		}
		
		ChunkStored chunk = new ChunkStored (new String(), 
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