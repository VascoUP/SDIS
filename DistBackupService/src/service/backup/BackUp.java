package service.backup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import information.Chunk;
import information.Storable;
import message.backup.BackUpMessage;
import message.backup.StoredMessage;
import message.general.MessageConst;
import protocol.backup.RequestBackUp;
import service.general.PontualService;

public class BackUp extends PontualService implements Storable {
	private String filePath;
	
	public BackUp(String filePath) throws IOException {
		super();
		
		protocol = new RequestBackUp();
		this.filePath = filePath;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public boolean validateMessage(byte[] message) {
		StoredMessage stm = new StoredMessage(message);
		BackUpMessage bum = (BackUpMessage)protocol.getMessage();
		
		return (stm.getMessageType().equals(MessageConst.STORED_MESSAGE_TYPE) &&
				stm.getFileId() == bum.getFileId() &&
				stm.getChunkId() == bum.getChunkId());
	}
	
	public void createChunks() throws IOException {
		int offset = 0;
		int IDchunk = 1;
		String fileID = Chunk.getFileId(filePath);
		
		Path path = Paths.get(filePath);
		byte[] buffer = Files.readAllBytes(path);
		
		RequestBackUp rbu = (RequestBackUp) protocol;
		
		
		while (offset < buffer.length) {
			
			int size = (offset + MessageConst.CHUNKSIZE >= buffer.length) ? 
							buffer.length - offset : 
							MessageConst.CHUNKSIZE;
			byte[] newBuffer = new byte[size];
			
			System.arraycopy(buffer, offset, newBuffer, 0, size);
						
			rbu.setMessage(fileID, IDchunk, newBuffer);
			run_service();
	        
			IDchunk++;
			offset += MessageConst.CHUNKSIZE;
		}
		
		try {
			rbu.end_protocol();
		} catch (IOException e) {
			System.out.println("Error closing sockets");
			return ;
		}
	}

	@Override
	public void run() {
		 try {
			createChunks();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
