package workerHandlers;

import java.io.IOException;
import java.util.Arrays;

import file.HandleFile;
import information.Chunk;
import information.ChunkStored;
import information.FileInfo;
import information.MessagesHashmap;
import information.PeerInfo;
import message.BasicMessage;
import message.InfoToMessage;
import message.MessageInfoPutChunk;
import message.MessageInfoStored;
import message.MessageToInfo;
import sender.AnswerBackUpSender;
import spacemanaging.SpaceManager;

/**
 * 
 * This class provides a handler that waits to store a chunk
 * This class extends the MessageServiceWait class
 *
 */
public class WaitStoreChunk extends MessageServiceWait {
	private MessageInfoPutChunk info;	//This class builds the PUTCHUNK_MESSAGE information
	private int prepdeg = -1;			//Perceived replication degree
	
	/**
	 * WaitStoreChunk's constructor
	 * @param time Service's time
	 * @param message Basic message
	 */
	public WaitStoreChunk(long time, BasicMessage message) {
		super(time, message);
	}
	
	/**
	 * Initiates the MessageInfoPutChunk
	 */
	private void initInfo() {
		if( info == null )
			info = (MessageInfoPutChunk) MessageToInfo.messageToInfo(message);
	}
	
	/**
	 * Initiates the perceived replication degree
	 */
	private void getValue() {
		initInfo();
		
		MessageInfoPutChunk backupMessage = (MessageInfoPutChunk) info;
		MessageInfoStored m1 = new MessageInfoStored(
									PeerInfo.peerInfo.getVersionProtocol(), 
									PeerInfo.peerInfo.getServerID(), 
									backupMessage.getFileID(), 
									backupMessage.getChunkID());
		BasicMessage m2 = InfoToMessage.toMessage(m1);
		if( m2 != null )
			prepdeg = MessagesHashmap.getSize(m2);
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
		getValue();
		initInfo();
		
		System.out.println("WaitStoreChunk: capacity	 " + SpaceManager.instance.getCapacity());
		System.out.println("WaitStoreChunk: stored size 	" + FileInfo.getStoredSize());
		System.out.println("WaitStoreChunk: info size	 " + info.getChunk().length);
		return 	((	info != null && 
					prepdeg < info.getReplicationDegree()
				) ||
				(	FileInfo.findStoredChunk(info.getFileID(), info.getChunkID()) != null &&
					equalChunks()
				)) &&
				FileInfo.getStoredSize() + info.getChunk().length <= SpaceManager.instance.getCapacity();
	}
	
	/**
	 * Creates the service associated to WaitStoreChunk
	 */
	@Override
	protected void service() {
		Chunk chunk;
		String fileName, fileID;
		int chunkID;

		initInfo();
		
		fileID = info.getFileID();
		chunkID = info.getChunkID();
		fileName = HandleFile.getFileName(fileID, chunkID);
		
		chunk = new ChunkStored(fileName, fileID, chunkID, prepdeg + 1, info.getChunk());
		try {
			AnswerBackUpSender abup = new AnswerBackUpSender(
					new MessageInfoStored(
						PeerInfo.peerInfo.getVersionProtocol(),
						PeerInfo.peerInfo.getServerID(),
						fileID, 
						chunkID));
			abup.execute();
			FileInfo.addStoredChunk((ChunkStored)chunk);
			HandleFile.writeFile(info.getChunk(), fileName);
		} catch (IOException ignore) {
		}
	}
	
	/**
	 * Starts the service provided
	 * @param time Service's time
	 * @param message Basic message
	 */
	public static void serve(long time, BasicMessage message) {
		WaitStoreChunk wsc = new WaitStoreChunk(time, message);
		wsc.start();
	}
}
