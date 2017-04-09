package sender;

import java.io.IOException;

import connection.ConnectionConstants;
import information.ChunkBackedUp;
import information.ChunkStored;
import information.FileInfo;
import information.MessagesHashmap;
import information.PeerInfo;
import message.BasicMessage;
import message.InfoToMessage;
import message.MessageInfoPutChunk;
import message.MessageInfoStored;

/**
 * 
 * This class builds a backup sender
 *
 */
public class BackUpSender extends ChannelSender {
	private static final int MAX_NUMBER_TRIES = 3;		//Maximum number of tries
	private final String filePath;						//File's pathname
	private boolean addToFile;							//Info telling the class if this should update the XML file
	private int prepdeg = -1;							//Perceived replication degree
	
	/**
	 * BackUpSender's constructor
	 * @param filePath File's pathname
	 * @param message PutChunk's message
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	public BackUpSender(String filePath, boolean addToFile, MessageInfoPutChunk message) throws IOException {
		super( message, ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
		this.filePath = filePath;
	}
	
	/**
	 * Gets the perceived replication degree from the stored message
	 * @return The perceived replication degree
	 */
	private int getMessages() {		
		MessageInfoPutChunk backupMessage = (MessageInfoPutChunk) message;
		MessageInfoStored m1 = new MessageInfoStored(
				PeerInfo.peerInfo.getVersionProtocol(), 
				PeerInfo.peerInfo.getServerID(), 
				backupMessage.getFileID(), 
				backupMessage.getChunkID());
		BasicMessage m2 = InfoToMessage.toMessage(m1);
		if( m2 != null )
			prepdeg = MessagesHashmap.getSize(m2);
		return prepdeg;
	}
	
	/**
	 * Removes the messages from the HashMap
	 */
	private void removeMessages() {
		MessageInfoPutChunk backupMessage = (MessageInfoPutChunk) message;
		MessageInfoStored m1 = new MessageInfoStored(
									PeerInfo.peerInfo.getVersionProtocol(), 
									PeerInfo.peerInfo.getServerID(), 
									backupMessage.getFileID(), 
									backupMessage.getChunkID());
		BasicMessage m2 = InfoToMessage.toMessage(m1);
		if( m2 != null )
			MessagesHashmap.removeKey(m2);
	}
	
	/**
	 * Adds a backed up file
	 */
	private void fileAdd() {
		MessageInfoPutChunk backupMessage = (MessageInfoPutChunk) message;
		
		if( addToFile )
			FileInfo.addBackedUpChunk(
					new ChunkBackedUp(
							filePath, 
							backupMessage.getFileID(), 
							backupMessage.getChunkID(),
							backupMessage.getReplicationDegree(),
							prepdeg));
		else
			FileInfo.addStoredChunk(
					new ChunkStored(
						filePath, 
						backupMessage.getFileID(), 
						backupMessage.getChunkID(),
						backupMessage.getReplicationDegree(),
						prepdeg + 1, 
						new byte[0]));
	}
	
	/**
	 * Verifies if the perceived replication degree from a message is greater or equal that the replication degree from the backup message
	 * @return true if its equal or greater, false otherwise
	 */
	public boolean condition() {
		MessageInfoPutChunk backupMessage = (MessageInfoPutChunk) message;
		return getMessages() >= backupMessage.getReplicationDegree();
	}
	
	/**
	 * Executes the backup sender
	 */
	@Override
	public void execute() {
		int nTries = 0;
		
		do {
			sendMessage();
			cooldown(1000);			
		} while( !condition() && ++nTries < MAX_NUMBER_TRIES );
		
		if( prepdeg > 0 )
			fileAdd();
		removeMessages();
	}
}
