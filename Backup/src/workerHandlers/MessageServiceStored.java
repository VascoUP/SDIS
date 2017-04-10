package workerHandlers;

import information.ChunkStored;
import information.FileInfo;
import message.BasicMessage;
import message.MessageInfoStored;
import message.MessageToInfo;

public class MessageServiceStored extends MessageService {

	public MessageServiceStored(long time, BasicMessage message) {
		super(time, message);
	}
	
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

	public static void serve(long time, BasicMessage message) {
		MessageServiceStored service = new MessageServiceStored(time, message);
		service.updatePRepDeg();
	}
}
