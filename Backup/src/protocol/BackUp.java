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

/**
 * 
 * This class creates the BackUp protocol
 * This implements the Protocol interface
 *
 */
public class BackUp implements Protocol {
	private String filePath;			//File's pathname
	private String fileID;				//File's ID
	private int replicationDegree;		//Replication's degree
	private int chunkID;				//Chunk's ID
	private byte[] chunk;				//Chunk's content

	/**
	 * BackUp's constructor
	 * @param filePath File's pathname
	 * @param replicationDegree Replication's degree
	 */
	public BackUp(String filePath, int replicationDegree) {
		super();
		
		this.filePath = filePath;
		this.fileID = Chunk.getFileId(filePath);
		this.replicationDegree = replicationDegree;
		this.chunkID = 1;
	}
	
	/**
	 * Gets the next chunk
	 * @param offset Offset to use to obtain the next chunk
	 * @param buffer Chunk's buffer
	 * @return The byte's array with the next chunk's content
	 */
	private byte[] getNextChunk(int offset, byte[] buffer) {
		int size = (offset + MessageConst.CHUNKSIZE > buffer.length) ? 
						buffer.length - offset : 
						MessageConst.CHUNKSIZE;
		byte[] newBuffer = new byte[size];
		
		System.arraycopy(buffer, offset, newBuffer, 0, size);
		return newBuffer;
	}
	
	/**
	 * Initializes the backup's sender
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
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
	
	/**
	 * Runs the backup's service
	 */
	@Override
	public void run_service() {
		try {
			initialize_sender();
		} catch (IOException ignore) {
		}
	}
}
