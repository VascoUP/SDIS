package service.backup;

import java.io.IOException;

import file.HandleXMLFile;
import information.Chunk;
import information.Storable;
import message.backup.BackUpMessage;
import protocol.backup.AnswerBackUp;
import service.general.ContinuousService;
import ui.App;

public class WaitBackUp extends ContinuousService implements Storable {
	
	public WaitBackUp() throws IOException {
		super();
		
		protocol = new AnswerBackUp();
	}
	
	public BackUpMessage get_message() throws IOException {
		AnswerBackUp abu = (AnswerBackUp) protocol;
		BackUpMessage bum;
		byte[] rcv = abu.receive();
		
		try {
			bum = new BackUpMessage(rcv);
		} catch( Error e ) {
			System.out.println(e);
			return null;
		}
		
		return bum;
	}
	
	public boolean handle_message(BackUpMessage bum) throws IOException {
		AnswerBackUp abu = (AnswerBackUp) protocol;
		String fileName, fileID;
		int chunkID, serverID;
		Chunk chunk;
		
		serverID = bum.getSenderId();
		if( serverID == App.getServerId() )
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
		BackUpMessage bum = get_message();
		
		randomWait();
		
		handle_message(bum);
	}
}

