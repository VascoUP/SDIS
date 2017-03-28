package service.backup;

import java.io.IOException;

import file.HandleXMLFile;
import information.Chunk;
import information.Storable;
import message.backup.BackUpMessage;
import message.general.Message;
import protocol.backup.AnswerBackUp;
import service.general.ContinuousService;
import ui.App;

public class WaitBackUp extends ContinuousService implements Storable {
	
	public WaitBackUp() throws IOException {
		super();
		
		protocol = new AnswerBackUp();
	}
	
	public byte[] get_message() throws IOException {
		AnswerBackUp abu = (AnswerBackUp) protocol;
		return abu.receive();
	}
	
	public Message validateMessage(byte[] message) {
		BackUpMessage bum;
		
		try {
			bum = new BackUpMessage(message);
		} catch( Error e ) {
			return null;
		}
		
		return (bum.getSenderId() != App.getServerId()) ? bum : null;
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
		fileName = fileID + "_" + chunkID;
		
		chunk = new Chunk(fileName, fileID, chunkID, bum.getBody());
		chunk.store();
		try {
			HandleXMLFile.addChunk(chunk);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		abu.setMessage(fileID, chunkID);
		abu.send();
		
		return true;
	}
	
	public void run_service() throws IOException, InterruptedException  {
		byte[] rcv = get_message();
		
		randomWait();
		
		handle_message(rcv);
	}
}

