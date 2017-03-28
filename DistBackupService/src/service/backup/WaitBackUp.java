package service.backup;

import java.io.IOException;

import file.HandleFile;
import file.HandleXMLFile;
import information.Chunk;
import information.Storable;
import message.backup.BackUpMessage;
import message.general.Message;
import message.general.MessageConst;
import protocol.backup.AnswerBackUp;
import service.general.ContinuousService;
import ui.App;

public class WaitBackUp extends ContinuousService implements Storable {
	
	public WaitBackUp() throws IOException {
		super();
		
		protocol = new AnswerBackUp();
	}
		
	public Message validateMessage(byte[] message) {
		BackUpMessage bum;
		
		try {
			bum = new BackUpMessage(message);
		} catch( Error e ) {
			System.out.println("Message error");
			e.printStackTrace();
			return null;
		}
		
		return (bum.getMessageType().equals(MessageConst.PUTCHUNK_MESSAGE_TYPE) &&
				bum.getSenderId() != App.getServerId()) ? bum : null;
	}
	
	public boolean handle_message(byte[] message) throws IOException {
		AnswerBackUp abu = (AnswerBackUp) protocol;
		BackUpMessage bum;
		Chunk chunk;
		String fileName, fileID;
		int chunkID;
		
		if ((bum = (BackUpMessage) validateMessage(message)) == null)
			return false;
				
		fileID = bum.getFileId();
		chunkID = bum.getChunkId();
		fileName = HandleFile.getFileName(fileID, chunkID);
		
		chunk = new Chunk(fileName, fileID, chunkID, bum.getBody());
		chunk.store();
		try {
			HandleXMLFile.addChunk(chunk);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		abu.setMessage(fileID, chunkID);
		send();
		
		return true;
	}
	
	public void run_service() throws IOException, InterruptedException  {
		byte[] rcv = receive();
		
		randomWait();
		
		handle_message(rcv);
	}
}

