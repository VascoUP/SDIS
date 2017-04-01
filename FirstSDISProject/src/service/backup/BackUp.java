package service.backup;

public class BackUp {
/*
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
		
		return (stm.isValidMessage() &&
				stm.getFileId().equals(bum.getFileId()) &&
				stm.getChunkId() == bum.getChunkId()) ? stm : null;
	}


	public void setMessage(String fileID, int chunkID, byte[] buffer) {
		RequestBackUp rbu = (RequestBackUp) protocol;
		rbu.setMessage(fileID, chunkID, buffer);
	}
	
	
	public void storeChunkInfo(String filePath, String fileID, int chunkID) {
		Chunk c = new Chunk(filePath, fileID, chunkID);
		try {
			c.backUp();
		} catch (IOException e) {
			e.printStackTrace();
			return ;
		}
		FileInfo.addBackedUpChunk(c);
	}
	
	public int getPercentage(int offset, int size) {
		int percentage;
		if( (percentage = size - offset / 100) > 100 )
			percentage = 100;
		return percentage;
	}
	
	public byte[] getNextChunk(int offset, byte[] buffer) {
		int size = (offset + MessageConst.CHUNKSIZE > buffer.length) ? 
						buffer.length - offset : 
						MessageConst.CHUNKSIZE;
		byte[] newBuffer = new byte[size];
		
		System.arraycopy(buffer, offset, newBuffer, 0, size);
		return newBuffer;
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
	
	public void service() {
		if(!send())
			return ;
		getAnswer();
	}
	
	public void run_service() {
		int offset = 0, chunkID = 1;
		String fileID = Chunk.getFileId(filePath);
		
		byte[] buffer;
		try {
			buffer = HandleFile.readFile(filePath);
		} catch (IOException e) {
			e.printStackTrace();
			return ;
		}
		
		if( buffer == null )
			return ;
		
		byte[] chunk;
		
		while (offset <= buffer.length) {
			chunk = getNextChunk(offset, buffer);
			
			setMessage(fileID, chunkID, chunk);
			service();
			storeChunkInfo(filePath, fileID, chunkID);
			
			chunkID++;
			offset += MessageConst.CHUNKSIZE;
		}
	}

	
	@Override
	public void run() {
		FileInfo.eliminateBackedUpFile(filePath);
		run_service();
		end_service();
	}
*/
}
