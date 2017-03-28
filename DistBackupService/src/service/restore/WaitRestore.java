package service.restore;

import java.io.IOException;

import file.HandleFile;
import information.Storable;
import message.general.Message;
import message.general.MessageConst;
import message.restore.GetChunkMessage;
import protocol.restore.SendChunk;
import service.general.ContinuousService;

public class WaitRestore extends ContinuousService implements Storable {
	
	public WaitRestore() throws IOException {
		super();
		
		protocol = new SendChunk();
	}
	
	public byte[] get_message() throws IOException {
		SendChunk abu = (SendChunk) protocol;
		return abu.receive();
	}
	
	public Message validateMessage(byte[] message) {
		GetChunkMessage gcm;
		
		try {
			gcm = new GetChunkMessage(message);
		} catch (Error e) {
			return null;
		}
		
		return gcm.getMessageType().equals(MessageConst.RESTORE_MESSAGE_TYPE) ? gcm : null;
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
		fileName = fileID + "_" + chunkID;
		
		chunk = HandleFile.readFile(fileName);
				
		sc.setMessage(fileID, chunkID, chunk);
		sc.send();
		
		return true;
	}
	
	public void run_service() throws IOException, InterruptedException  {
		byte[] rcv = get_message();
		
		randomWait();
		
		handle_message(rcv);
	}
}
