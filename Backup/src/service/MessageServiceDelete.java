package service;

import information.Chunk;
import information.FileInfo;
import information.MessagesHashmap;
import message.BasicMessage;
import message.MessageInfoDelete;
import message.MessageToInfo;

public class MessageServiceDelete extends MessageService {
	public MessageServiceDelete(long time, BasicMessage message) {
		super(time, message);
	}
	
	public static void serve(long time, BasicMessage message) {
		MessageServiceDelete sd = new MessageServiceDelete(time, message);
		sd.deleteFiles();
	}
	
	private void deleteFiles() {
		MessageInfoDelete info = (MessageInfoDelete) MessageToInfo.messageToInfo(message);
		Chunk[] chunks = FileInfo.findAllStoredChunks(info.getFileID());
		
		for( Chunk c : chunks )
			FileInfo.eliminateSameStoredChunk(c);
		
		MessagesHashmap.addMessage(message);
	}
}
