package service.restore;

import java.io.IOException;
import java.util.ArrayList;

import file.HandleFile;
import information.AppInfo;
import information.Chunk;
import information.Storable;
import message.general.Message;
import message.restore.ChunkMessage;
import message.restore.GetChunkMessage;
import protocol.restore.GetChunk;
import service.general.PontualService;
import service.general.ServiceConst;

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
				
		return (cm.isValidMessage() &&
				cm.getFileId().equals(bum.getFileId()) &&
				cm.getChunkId() == bum.getChunkId()) ? cm : null;
	}

	public byte[] getAnswer() {
		long t = System.currentTimeMillis();
		long end = t + 1000;
		byte[] rcv;
		Message m;
		
		while((t = end - System.currentTimeMillis()) > 0 ){
			rcv = receive((int)t);

			if( rcv == null )
				continue;
			
			if( (m = validateMessage(rcv)) != null)
				return m.getBody();
		}
		
		return null;
	}
	
	public void setMessage(String fileID, int chunkID) {
		GetChunk prot = (GetChunk)protocol;
		prot.setMessage(fileID, chunkID);
	}
	
	public String getFileID() {
		Chunk buChunk = AppInfo.findBackedUpChunk(filePath);
		if( buChunk == null ) {
			System.out.println("Restore of a non backed up file");
			return null;
		}
		return buChunk.getFileId();
	}
	
	public void writeArrayList(ArrayList<byte[]> chunks) {
		try {
			HandleFile.writeFile(chunks, filePath);
		} catch (IOException e) {
			System.out.println("Error writting to file " + filePath);
		}
	}

	public void run_service() {
		ArrayList<byte[]> chunks = new ArrayList<byte[]>();
		String fileID = getFileID();
		int chunkID = 1, nTries = 0;
		
		if( fileID == null )
			return;
		
		setMessage(fileID, chunkID);
		
		while( nTries < ServiceConst.MAXIMUM_TRIES ) {
			if( !send() )
				return;
			
			byte[] chunk = getAnswer();
			if( chunk == null ) {
				nTries++;
				continue;
			}
			
			System.out.println("ID: " + chunkID + " --> " + chunk.length);
			
			chunks.add(chunk);
			if( chunk.length < 64000 )
				break;

			setMessage(fileID, ++chunkID);
			nTries = 0;
		}
		
		if( nTries >= ServiceConst.MAXIMUM_TRIES )
			System.out.println("Error restoring the file " + filePath);
		else
			writeArrayList(chunks);
	}
}
