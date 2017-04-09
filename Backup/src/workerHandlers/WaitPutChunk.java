package workerHandlers;

import java.io.IOException;

import file.HandleFile;
import information.ChunkStored;
import information.FileInfo;
import information.MessagesHashmap;
import information.PeerInfo;
import information.Version;
import message.BasicMessage;
import message.InfoToMessage;
import message.MessageInfoPutChunk;
import message.MessageInfoRemoved;
import message.MessageToInfo;
import sender.BackUpSender;
import threads.ThreadManager;

public class WaitPutChunk extends MessageServiceWait {
	private MessageInfoRemoved info;	//This class builds the RESTORE_MESSAGE information

	public WaitPutChunk(long time, BasicMessage message) {
		super(time, message);
		info = (MessageInfoRemoved) MessageToInfo.messageToInfo(message);
	}
	
	private boolean canInitiateProtocol() {
				// If the file is backedup
		return 	FileInfo.findBackedUpChunk(info.getFileID(), info.getChunkID()) == null && 
				// Or, if the file is not stored
				FileInfo.findStoredChunk(info.getFileID(), info.getChunkID()) != null;
	}
	
	/**
	 * Verifies if the MessageInfoGetChunk and the basic message created aren't null and if the hashmap size is less than 1
	 * @return true if the condition is verified, false otherwise
	 */
	@Override
	public boolean condition() {
		MessageInfoRemoved restoreMessage = (MessageInfoRemoved) info;
		MessageInfoPutChunk m1 = new MessageInfoPutChunk(
								Version.instance.getVersionProtocol(),
								PeerInfo.peerInfo.getServerID(), 
								restoreMessage.getFileID(), 
								restoreMessage.getChunkID(),
								0,
								new byte[0]);
		BasicMessage m2 = InfoToMessage.toMessage(m1);
		System.out.println("WaitPutChunk: condition " + MessagesHashmap.getSize(m2));
		
		return 	info != null && m2 != null &&
				MessagesHashmap.getSize(m2) < 1;
	}
	
	/**
	 * Creates the service associated to WaitGetChunk
	 */
	@Override
	public void service() {
		byte[] data;
		String fileName, fileID;
		int chunkID;
		
				
		fileID = info.getFileID();
		chunkID = info.getChunkID();
		ChunkStored chunk = FileInfo.findStoredChunk(fileID, chunkID);
		fileName = HandleFile.getFileName(fileID, chunkID);
		
		try {
			data = HandleFile.readFile(fileName);
			if( data == null )
				return ;
			System.out.println("Initializing BackUpSender");
			ThreadManager.initBackUp(
					new BackUpSender(
						fileName,
						false,
						new MessageInfoPutChunk(
								Version.instance.getVersionProtocol(),
								PeerInfo.peerInfo.getServerID(),
								fileID, 
								chunkID,
								chunk.getDRepDeg(), 
								data)));
		} catch (IOException ignore) {
		}
	}
	
	/**
	 * Starts the service
	 */
	public void start() {
		if( !canInitiateProtocol() )
			return ;
		
		MessagesHashmap.removeKey(message);
		if( randomWait() && condition() )
			service();
		else 
			System.out.println("Didnt passe the condition");
	}
	
	/**
	 * Starts the service provided
	 * @param time Service's time
	 * @param message Basic message
	 */
	public static void serve(long time, BasicMessage message) {
		WaitPutChunk pc = new WaitPutChunk(time, message);
		pc.start();
	}

}
