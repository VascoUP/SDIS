package workerHandlers;

import java.io.IOException;

import file.HandleFile;
import information.MessagesHashmap;
import information.PeerInfo;
import message.BasicMessage;
import message.InfoToMessage;
import message.MessageInfoChunk;
import message.MessageInfoGetChunk;
import message.MessageToInfo;
import sender.AnswerRestoreSender;

/**
 * 
 * This class provides a handler that waits to get a chunk
 * This class extends the MessageServiceWait class
 *
 */
public class WaitGetChunk extends MessageServiceWait {
	private MessageInfoGetChunk info;	//This class builds the RESTORE_MESSAGE information

	/**
	 * WaitGetChunk's constructor
	 * @param time Service's time
	 * @param message Basic message
	 */
	public WaitGetChunk(long time, BasicMessage message) {
		super(time, message);
	}
	
	/**
	 * Initiates the MessageInfoGetChunk
	 */
	public void initInfo() {
		if( info == null )
			info = (MessageInfoGetChunk) MessageToInfo.messageToInfo(message);
	}
	
	/**
	 * Verifies if the MessageInfoGetChunk and the basic message created aren't null and if the hashmap size is less than 1
	 * @return true if the condition is verified, false otherwise
	 */
	@Override
	public boolean condition() {
		initInfo();
				
		MessageInfoGetChunk restoreMessage = (MessageInfoGetChunk) info;
		MessageInfoChunk m1 = new MessageInfoChunk(
								PeerInfo.peerInfo.getVersionProtocol(), 
								PeerInfo.peerInfo.getServerID(), 
								restoreMessage.getFileID(), 
								restoreMessage.getChunkID(),
								new byte[0]);
		BasicMessage m2 = InfoToMessage.toMessage(m1);
		
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
		
		initInfo();
				
		fileID = info.getFileID();
		chunkID = info.getChunkID();
		fileName = HandleFile.getFileName(fileID, chunkID);
		
		try {
			data = HandleFile.readFile(fileName);
			if( data == null )
				return ;
			
			AnswerRestoreSender abup = new AnswerRestoreSender(
					new MessageInfoChunk(
						PeerInfo.peerInfo.getVersionProtocol(),
						PeerInfo.peerInfo.getServerID(),
						fileID, 
						chunkID,
						data));
			abup.execute();
		} catch (IOException ignore) {
		}
	}
	
	/**
	 * Starts the service provided
	 * @param time Service's time
	 * @param message Basic message
	 */
	public static void serve(long time, BasicMessage message) {
		WaitGetChunk gc = new WaitGetChunk(time, message);
		gc.start();
	}
}