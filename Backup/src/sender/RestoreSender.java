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
	private Restore restoreObject;
	private int prepdeg;
	
	public RestoreSender(Restore restoreObject, MessageInfoGetChunk message) throws IOException {
		super( message, ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
		this.restoreObject = restoreObject;
	}
	
	private int getValue() {
		MessageInfoGetChunk restoreMessage = (MessageInfoGetChunk) message;
		MessageInfoChunk m = new MessageInfoChunk(
								PeerInfo.peerInfo.getVersionProtocol(), 
								PeerInfo.peerInfo.getServerID(), 
								restoreMessage.getFileID(), 
								restoreMessage.getChunkID(),
								null);
		prepdeg = MessagesHashmap.getValue(InfoToMessage.toMessage(m));
		System.out.println("RestoreSender " + prepdeg);
		return prepdeg;
	}
	
	private int getMessages() {
		if( this.prepdeg == -1 )
			getValue();
		return this.prepdeg;
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
		return getValue() >= 1;
	}
	
	@Override
	public void execute() {
		do {
			sendMessage();
			cooldown(1000);
		} while( !condition() );

		signalRestore();
		removeMessages();
	}
}