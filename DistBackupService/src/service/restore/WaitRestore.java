package service.restore;

import java.io.IOException;

import file.HandleFile;
import information.Storable;
import message.general.Message;
import message.general.MessageConst;
import message.restore.ChunkMessage;
import message.restore.GetChunkMessage;
import protocol.restore.SendChunk;
import service.general.ContinuousService;
import ui.App;

public class WaitRestore extends ContinuousService implements Storable {
	
	public WaitRestore() throws IOException {
		super();
		
		protocol = new SendChunk();
	}
	
	public byte[] get_message() throws IOException {
		SendChunk abu = (SendChunk) protocol;
		return abu.receive();
	}	
	
	public boolean checkOtherAnswers(int time) {
		long t = System.currentTimeMillis();
		long end = t + time;
		byte[] rcv;
		
		while((t = end - System.currentTimeMillis()) > 0 ){
			rcv = receive((int)t);
			
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
		
		return (gcm.getMessageType().equals(MessageConst.RESTORE_MESSAGE_TYPE) &&
				gcm.getSenderId() != App.getServerId())? gcm : null;
	}
	
	public boolean sameMessage(byte[] message) {
		ChunkMessage gcm;
		ChunkMessage mGcm = (ChunkMessage)protocol.getMessage();
		
		try {
			gcm = new ChunkMessage(message);
		} catch (Error e) {
			return false;
		}
		
		return 	gcm.getMessageType().equals(MessageConst.CHUNK_MESSAGE_TYPE) &&
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
			sc.send();
		
		return true;
	}
	
	public void run_service() throws IOException, InterruptedException  {
		byte[] rcv = get_message();
		handle_message(rcv);
	}
}
