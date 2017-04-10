package workerHandlers;

import java.io.IOException;
import java.util.Arrays;

import file.HandleFile;
import information.Chunk;
import information.ChunkStored;
import information.FileInfo;
import information.MessagesHashmap;
import information.PeerInfo;
import information.Version;
import message.BasicMessage;
import message.InfoToMessage;
import message.MessageInfoPutChunk;
import message.MessageInfoStored;
import message.MessageToInfo;
import sender.AnswerBackUpSender;

/**
 * 
 * This class provides a handler that waits to store a chunk
 * This class extends the MessageServiceWait class
 *
 */
public abstract class WaitStoreChunk extends MessageServiceWait {
	protected MessageInfoPutChunk info;	//This class builds the PUTCHUNK_MESSAGE information
	protected int prepdeg = -1;			//Perceived replication degree
	
	/**
	 * WaitStoreChunk's constructor
	 * @param time Service's time
	 * @param message Basic message
	 */
	public WaitStoreChunk(long time, BasicMessage message) {
		super(time, message);
		info = (MessageInfoPutChunk) MessageToInfo.messageToInfo(message);
	}
	
	public void updatePRepDeg(int prepdeg) {
		MessageInfoPutChunk stored = (MessageInfoPutChunk) MessageToInfo.messageToInfo(message);
		ChunkStored cStored = FileInfo.findStoredChunk(stored.getFileID(), stored.getChunkID());
		if( cStored == null )
			return ;
		cStored.setPRepDeg(prepdeg);
	}
	
	/**
	 * Initiates the perceived replication degree
	 */
	protected void getValue() {		
		MessageInfoPutChunk backupMessage = (MessageInfoPutChunk) info;
		MessageInfoStored m1 = new MessageInfoStored(
									Version.instance.getVersionProtocol(),
									PeerInfo.peerInfo.getServerID(), 
									backupMessage.getFileID(), 
									backupMessage.getChunkID());
		BasicMessage m2 = InfoToMessage.toMessage(m1);
		if( m2 != null )
			prepdeg = MessagesHashmap.getSize(m2);
	}
	
	/**
	 * Verifies if the chunk request has already been backed up by this peer
	 * @return true if it has, false otherwise
	 */
	protected boolean hasBackedUpChunk() {
		return FileInfo.findBackedUpChunk(info.getFileID(), info.getChunkID()) != null;
	}
	
	/**
	 * Verifies if the chunk requested has already been stored by this peer
	 * @return true if it has, false otherwise
	 */
	protected boolean hasStoredChunk() {
		return FileInfo.findStoredChunk(info.getFileID(), info.getChunkID()) != null && equalChunks();
	}
	
	/**
	 * Verifies if the chunks are equal
	 * @return true if they are equal, false otherwise
	 */
	private boolean equalChunks() {
		String pathName = HandleFile.getFileName(info.getFileID(), info.getChunkID());
		byte[] chunk;
		try {
			chunk = HandleFile.readFile(pathName);
		} catch (IOException e) {
			return false;
		}
		
		return chunk != null && Arrays.equals(chunk, info.getChunk());		
	}
	
	/**
	 * Verifies a group of conditions that makes possible the service's execution
	 * @return true if the conditions is verified, false otherwise
	 */
	@Override
	public boolean condition() {
		System.err.println("Running in the wrong class");
		return false;
	}

	/**
	 * Creates the message and sends it
	 * @throws IOException
	 */
	private void sendMessage() throws IOException {
		AnswerBackUpSender abup = new AnswerBackUpSender(
				new MessageInfoStored(
					Version.instance.getVersionProtocol(),
					PeerInfo.peerInfo.getServerID(),
					info.getFileID(), 
					info.getChunkID()));
		abup.execute();
	}
	
	/**
	 * Creates the service associated to WaitStoreChunk
	 */
	@Override
	protected void service() {
		Chunk chunk;
		String fileName, fileID;
		int chunkID;
		
		fileID = info.getFileID();
		chunkID = info.getChunkID();
		fileName = HandleFile.getFileName(fileID, chunkID);
		chunk = new ChunkStored(fileName, fileID, chunkID, info.getReplicationDegree(), prepdeg + 1, info.getChunk());
		try {
			sendMessage();
			FileInfo.addStoredChunk((ChunkStored)chunk);
			HandleFile.writeFile(info.getChunk(), fileName);
		} catch (IOException ignore) {
		}
	}

	/**
	 * Starts the service
	 */
	@Override
	public void start() {
		if( hasBackedUpChunk() )
			return ;
		
		if( hasStoredChunk() ) {
			updatePRepDeg(1);
			if( !randomWait() ) 
				return ;
			try {
				sendMessage();
			} catch (IOException ignore) {
			}
			return ;
		}
		
		MessageInfoPutChunk restoreMessage = (MessageInfoPutChunk) info;
		MessageInfoStored m1 = new MessageInfoStored(
								Version.instance.getVersionProtocol(),
								PeerInfo.peerInfo.getServerID(), 
								restoreMessage.getFileID(), 
								restoreMessage.getChunkID());
		BasicMessage m2 = InfoToMessage.toMessage(m1);
		MessagesHashmap.removeKey(m2);
		
		if( randomWait() && condition() )
			service();
	}
	
	/**
	 * Starts the service provided
	 * @param time Service's time
	 * @param message Basic message
	 */
	public static void serve(long time, BasicMessage message) {
		WaitStoreChunk wsc;
		if( Version.isEnhanced() )
			wsc = new WaitStoreChunkEnhancedVersion(time, message);
		else
			wsc = new WaitStoreChunkBasicVersion(time, message);
		wsc.start();
	}
}
