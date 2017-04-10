package workerHandlers;

import information.ChunkStored;
import information.FileInfo;
import message.BasicMessage;
import message.MessageInfoStored;
import message.MessageToInfo;

/**
 * 
 * This class builds the message's service for stored
 * This extends the MessageService class
 *
 */
public class MessageServiceStored extends MessageService {

	/**
	 * MessageServiceStored's constructor
	 * @param time Message service's time
	 * @param message Basic Message
	 */
	public MessageServiceStored(long time, BasicMessage message) {
		super(time, message);
	}
	
	/**
	 * Updates the perceived replication degree
	 */
	public void updatePRepDeg() {
		MessageInfoStored stored = (MessageInfoStored) MessageToInfo.messageToInfo(message);
		ChunkStored cStored = FileInfo.findStoredChunk(stored.getFileID(), stored.getChunkID());
		if( cStored == null )
			return ;
		cStored.setPRepDeg(1+cStored.getPRepDeg());
		FileInfo.updateStoredChunk(cStored);
		System.out.println("MessageServiceStored: Update PREPDEG of \n" + 
				stored.getFileID() + " - " + stored.getChunkID() + "\n" +
				"to " + cStored.getPRepDeg());
	}

	/**
	 * Creates a new message's service and updates the perceived replication degree
	 * @param time Message service's time
	 * @param message Basic message
	 */
	public static void serve(long time, BasicMessage message) {
		MessageServiceStored service = new MessageServiceStored(time, message);
		service.updatePRepDeg();
	}
}
