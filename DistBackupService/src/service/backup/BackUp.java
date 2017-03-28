package service.backup;

import java.io.IOException;

import file.HandleFile;
import information.Chunk;
import information.Storable;
import message.backup.BackUpMessage;
import message.backup.StoredMessage;
import message.general.Message;
import message.general.MessageConst;
import protocol.backup.RequestBackUp;
import service.general.PontualService;
import ui.App;

public class BackUp extends PontualService implements Storable {
	private String filePath;
	
	public BackUp(String filePath) throws IOException {
		super();
		
		protocol = new RequestBackUp();
		this.filePath = filePath;
	}
	
	public Message validateMessage(byte[] message) {
		StoredMessage stm; 
		BackUpMessage bum = (BackUpMessage)protocol.getMessage();
		
		try {
			stm = new StoredMessage(message);
		} catch( Error e ) {
			return null;
		}
		
		return (stm.getMessageType().equals(MessageConst.STORED_MESSAGE_TYPE) &&
				stm.getFileId().equals(bum.getFileId()) &&
				stm.getChunkId() == bum.getChunkId() &&
				stm.getSenderId() != App.getServerId()) ? stm : null;
	}
	
	public int getAnswer() {
		int i = 0;
		long t = System.currentTimeMillis();
		long end = t + 1000;
		byte[] rcv;
		
		while((t = end - System.currentTimeMillis()) > 0 ){
			rcv = receive((int)t);
			
			if( rcv != null && validateMessage(rcv) != null )
				i++;
		}
		
		return i;
	}
	
	public void run_service() {
		if(!send())
			return ;
		getAnswer();
	}
	
	public void createChunks() throws IOException {
		int offset = 0;
		int IDchunk = 1;
		String fileID = Chunk.getFileId(filePath);
		System.out.println(filePath);
		System.out.println(fileID);
		
		byte[] buffer = HandleFile.readFile(filePath);
		
		RequestBackUp rbu = (RequestBackUp) protocol;
		
		
		while (offset <= buffer.length) {
			
			int size = (offset + MessageConst.CHUNKSIZE > buffer.length) ? 
							buffer.length - offset : 
							MessageConst.CHUNKSIZE;
			byte[] newBuffer = new byte[size];
			
			System.arraycopy(buffer, offset, newBuffer, 0, size);
						
			rbu.setMessage(fileID, IDchunk, newBuffer);
			run_service();
			
			System.out.println(offset);
	        
			IDchunk++;
			offset += MessageConst.CHUNKSIZE;
		}
		
		System.out.println("end");
		
		try {
			rbu.end_protocol();
		} catch (IOException e) {
			System.out.println("Error closing sockets");
			e.printStackTrace();
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
