package service.backup;

import java.io.FileOutputStream;
import java.io.IOException;

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
	
	public void run_service() throws IOException, InterruptedException  {
		AnswerBackUp abu = (AnswerBackUp) protocol;
		int fileID, chunkID, serverID;
		FileOutputStream output;
		String fileName = "";
		byte[] rcv;
	
		rcv = abu.receive();
		
		BackUpMessage bum;
		
		try {
			bum = new BackUpMessage(rcv);
		} catch( Error e ) {
			System.out.println(e);
			return ;
		}
		
		serverID = bum.getSenderId();
		if( serverID != App.getServerId() )
			return ;
		
		fileID = bum.getFileId();
		chunkID = bum.getChunkId();
		
		fileName = "Chunk_" + chunkID;
		
		randomWait();
		
		output = new FileOutputStream(fileName);
		output.write(bum.getBody());
		
		output.close();
		
		abu.setMessage(fileID, chunkID);
		abu.send();
	}
}

