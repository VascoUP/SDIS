package service.restore;

import java.io.IOException;

import file.HandleFile;
import information.Chunk;
import information.Storable;
import message.general.Message;
import message.general.MessageConst;
import message.restore.ChunkMessage;
import message.restore.GetChunkMessage;
import protocol.restore.GetChunk;
import service.general.PontualService;
import service.general.ServiceConst;
import ui.App;

public class Restore extends PontualService implements Storable {	
	private String filePath;
	
	public Restore(String filePath) throws IOException {
		super();
		
		this.filePath = filePath;
		protocol = new GetChunk();
	}
	
	public Message validateMessage(byte[] message) {
		ChunkMessage cm; 
		GetChunkMessage bum = (GetChunkMessage)protocol.getMessage();
		
		
		try {
			cm = new ChunkMessage(message);
		} catch( Error e ) {
			return null;
		}
				
		return (cm.getMessageType().equals(MessageConst.CHUNK_MESSAGE_TYPE) &&
				cm.getFileId().equals(bum.getFileId()) &&
				cm.getChunkId() == bum.getChunkId() &&
				cm.getSenderId() != App.getServerId()) ? cm : null;
	}

	public byte[] getAnswer() {
		long t = System.currentTimeMillis();
		long end = t + 1000;
		byte[] rcv;
		Message m;
		
		while((t = end - System.currentTimeMillis()) > 0 ){
			rcv = receive((int)t);
			
			/*
			if( rcv != null && (m = validateMessage(rcv)) != null)
				return m.getBody();
			*/

			if( rcv == null )
				continue;
			
			if( (m = validateMessage(rcv)) != null)
				return m.getBody();
		}
		
		return null;
	}
	
	public boolean storeChunk(int chunkID, byte[] chunk) {
		try {
			if( chunkID == 1 )
				HandleFile.writeFile(chunk, filePath);
			else
				HandleFile.appendToFile(chunk, filePath);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return false;
	}
	
	public void run_service() {
		String fileID = Chunk.getFileId(filePath);
		GetChunk prot = (GetChunk)protocol;
		boolean end = false;
		int chunkID = 1, nTries = 0;
		prot.setMessage(fileID, chunkID);
		
		while( !end && nTries < ServiceConst.MAXIMUM_TRIES ) {
			if( !send() )
				return;
			
			byte[] chunk = getAnswer();
			if( chunk == null ) {
				nTries++;
				continue;
			}
			
			if( chunk.length < 64000 )
				end = true;

			if(storeChunk(chunkID, chunk))
				prot.setMessage(fileID, ++chunkID);
		}
	}
}
