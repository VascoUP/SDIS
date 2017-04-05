package service.backup;

import java.io.IOException;

import file.HandleFile;
import information.Chunk;
import information.PeerInfo;
import message.MessageConst;
import message.MessageInfoPutChunk;
import sender.BackUpSender;
import threads.ThreadManager;

public class BackUp {
	private String filePath;
	private String fileID;
	private int replicationDegree;

	public BackUp(String filePath, int replicationDegree) {
		super();
		
		this.filePath = filePath;
		this.fileID = Chunk.getFileId(filePath);
		this.replicationDegree = replicationDegree;
	}
	
	
	private byte[] getNextChunk(int offset, byte[] buffer) {
		int size = (offset + MessageConst.CHUNKSIZE > buffer.length) ? 
						buffer.length - offset : 
						MessageConst.CHUNKSIZE;
		byte[] newBuffer = new byte[size];
		
		System.arraycopy(buffer, offset, newBuffer, 0, size);
		return newBuffer;
	}
	
	
	public void run_service() {
		int offset = 0, chunkID = 1;
		byte[] buffer;
		byte[] chunk;
		
		try {
			buffer = HandleFile.readFile(filePath);
		} catch (IOException e) {
			return ;
		}
		
		if( buffer == null )
			return ;
		
		while (offset <= buffer.length) {
			chunk = getNextChunk(offset, buffer);
			
			try {
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
			} catch (IOException e) {
				return ;
			}
			
			chunkID++;
			offset += MessageConst.CHUNKSIZE;
		}
	}
}
