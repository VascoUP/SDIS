package service.restore;

import java.io.IOException;

import file.HandleFile;
import information.Storable;
import message.general.Message;
import message.restore.ChunkMessage;
import message.restore.GetChunkMessage;
import protocol.restore.GetChunk;
import protocol.restore.SendChunk;
import service.general.ContinuousService;

public class WaitRestore extends ContinuousService implements Storable {
	
	public WaitRestore() throws IOException {
		super();
		
		protocol = new SendChunk();
	}
		
	public boolean checkOtherAnswers(int time) {
		GetChunk gcp;
		try {
			gcp = new GetChunk();
		} catch (IOException e) {
			return false;
		}
		
		long t = System.currentTimeMillis();
		long end = t + time;
		byte[] rcv;
		
		while((t = end - System.currentTimeMillis()) > 0 ){
			rcv = receive(gcp, (int)t);
			if( rcv != null && sameMessage(rcv) )
				return true;
		}
		return false;
	}
	
	
	public Message validateMessage(byte[] message) {
		GetChunkMessage gcm;
		
		try {
			gcm = new GetChunkMessage(message);
		} catch (Error e) {
			return null;
		}
		
		return gcm.isValidMessage() ? gcm : null;
	}
	
	public boolean sameMessage(byte[] message) {
		ChunkMessage gcm;
		ChunkMessage mGcm = (ChunkMessage)protocol.getMessage();
		
		try {
			gcm = new ChunkMessage(message);
		} catch (Error e) {
			return false;
		}
		
		return 	gcm.isValidMessage() &&
				gcm.getFileId().equals(mGcm.getFileId()) &&
				gcm.getChunkId() == mGcm.getChunkId();
	}
	
	public boolean handle_message(byte[] message) throws IOException {
		SendChunk sc = (SendChunk) protocol;
		GetChunkMessage gcm;
		byte[] chunk;
		String fileName, fileID;
		int chunkID;
		
		if ((gcm = (GetChunkMessage) validateMessage(message)) == null)
			return false;
		
		fileID = gcm.getFileId();
		chunkID = gcm.getChunkId();
		fileName = HandleFile.getFileName(fileID, chunkID);

		chunk = HandleFile.readFile(fileName);
		if( chunk == null )
			return false;

		sc.setMessage(fileID, chunkID, chunk);
		
		int wait = randomTime();
		if( !checkOtherAnswers(wait) )
			send();
		
		return true;
	}
}
