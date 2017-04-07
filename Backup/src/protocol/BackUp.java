package protocol;

import java.io.IOException;

import file.HandleFile;
import information.Chunk;
import information.FileInfo;
import information.PeerInfo;
import message.MessageConst;
import message.MessageInfoPutChunk;
import sender.BackUpSender;
import threads.ThreadManager;

public class BackUp implements Protocol {
	private String filePath;
	private String fileID;
	private int replicationDegree;
	private int chunkID;
	private byte[] chunk;

	public BackUp(String filePath, int replicationDegree) {
		super();
		
		this.filePath = filePath;
		this.fileID = Chunk.getFileId(filePath);
		this.replicationDegree = replicationDegree;
		this.chunkID = 1;
	}
	
	
	private byte[] getNextChunk(int offset, byte[] buffer) {
		int size = (offset + MessageConst.CHUNKSIZE > buffer.length) ? 
						buffer.length - offset : 
						MessageConst.CHUNKSIZE;
		byte[] newBuffer = new byte[size];
		
		System.arraycopy(buffer, offset, newBuffer, 0, size);
		return newBuffer;
	}
	
	@Override
	public void initialize_sender() throws IOException {
		int offset = 0;
		byte[] buffer;
		
		FileInfo.eliminateBackedUpFile(filePath);
		if( (buffer = HandleFile.readFile(filePath)) == null )
			return ;
		
		while (offset <= buffer.length) {
			chunk = getNextChunk(offset, buffer);
			
			ThreadManager.initBackUp(
					new BackUpSender(
						filePath,
						new MessageInfoPutChunk(
								PeerInfo.peerInfo.getVersionProtocol(), 
								PeerInfo.peerInfo.getServerID(),
								fileID, 
								chunkID,
								replicationDegree, 
								chunk)));
			
			chunkID++;
			offset += MessageConst.CHUNKSIZE;
		}
	}
	
	@Override
	public void run_service() {
		try {
			initialize_sender();
		} catch (IOException ignore) {
		}
	}
}
