package service.backup;

import java.io.IOException;

import file.HandleFile;
import information.FileInfo;
import information.Chunk;
import message.backup.BackUpMessage;
import message.general.Message;
import protocol.backup.AnswerBackUp;
import protocol.backup.RequestBackUp;
import service.general.ContinuousService;

public class WaitBackUp extends ContinuousService {
	
	public WaitBackUp() throws IOException {
		super();
		
		protocol = new AnswerBackUp();
	}
	
	public boolean checkOtherAnswers(int time) {
		RequestBackUp rbu;
		try {
			rbu = new RequestBackUp();
		} catch (IOException e) {
			return false;
		}
		
		long t = System.currentTimeMillis();
		long end = t + time;
		byte[] rcv;
		
		while((t = end - System.currentTimeMillis()) > 0 ){
			rcv = receive(rbu, (int)t);

			if( rcv != null && rcv.equals(protocol.getMessage()) )
				return true;
		}
		return false;
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
		
		return bum.isValidMessage() ? bum : null;
	}
	
	public boolean handle_message(byte[] message) throws IOException, InterruptedException {
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
		FileInfo.fileAddStoredChunk(chunk);
		
		abu.setMessage(fileID, chunkID);
		randomWait();
		send();
		
		int wait = randomTime();
		checkOtherAnswers(wait);
		send();
		
		return true;
	}
}

