package sender;

import java.io.IOException;

import connection.ConnectionConstants;
import information.ChunkStored;
import information.MessagesHashmap;
import information.PeerInfo;
import information.Version;
import message.BasicMessage;
import message.InfoToMessage;
import message.MessageInfoChunk;
import message.MessageInfoGetChunk;
import protocol.Restore;

/**
 * 
 * This class builds a restore sender that extends a channel sender class
 *
 */
public class RestoreSender extends ChannelSender {
	private static final int MAX_NUMBER_TRIES = 3;		//Maximum number of tries
	private final Restore restoreObject;				//Restore object
	private int prepdeg = -1;							//Perceived replication degree
	
	/**
	 * RestoreSender's constructor
	 * @param restoreObject Restore object
	 * @param message MessageInfoGetChunk that will be used in the channel sender
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	public RestoreSender(Restore restoreObject, MessageInfoGetChunk message) throws IOException {
		super( message, ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
		this.restoreObject = restoreObject;
	}
	
	/**
	 * Gets the perceived replication degree from the basic message
	 * @return The perceived replication degree
	 */
	private int getMessages() {
		MessageInfoGetChunk restoreMessage = (MessageInfoGetChunk) message;
		MessageInfoChunk m1 = new MessageInfoChunk(
								Version.instance.getVersionProtocol(),
								PeerInfo.peerInfo.getServerID(), 
								restoreMessage.getFileID(), 
								restoreMessage.getChunkID(),
								new byte[0]);
		BasicMessage m2 = InfoToMessage.toMessage(m1);
		if( m2 != null )
			prepdeg = MessagesHashmap.getSize(m2);
		return prepdeg;
	}
	
	/**
	 * Removes the messages from the HashMap
	 */
	private void removeMessages() {
		MessageInfoGetChunk restoreMessage = (MessageInfoGetChunk) message;
		MessageInfoChunk m1 = new MessageInfoChunk(
								Version.instance.getVersionProtocol(),
								PeerInfo.peerInfo.getServerID(), 
								restoreMessage.getFileID(), 
								restoreMessage.getChunkID(),
								new byte[0]);
		BasicMessage m2 = InfoToMessage.toMessage(m1);
		if( m2 != null )
			MessagesHashmap.removeKey(m2);
	}
	
	/**
	 * Restores the signal when the program is receiving a basic message
	 */
	private void signalRestore() {
		MessageInfoGetChunk restoreMessage = (MessageInfoGetChunk) message;
		MessageInfoChunk m1 = new MessageInfoChunk(
								Version.instance.getVersionProtocol(),
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
				1,
				1,
				receivedMessage.getBody());
		restoreObject.addReceivedChunk(chunk);
	}
	
	/**
	 * Verifies if the perceived replication degree is greater or equal than 1
	 * @return true if it's equal or greater than 1, false otherwise
	 */
	public boolean condition() {
		return getMessages() >= 1;
	}
	
	/**
	 * Executes the restore channel sender
	 */
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