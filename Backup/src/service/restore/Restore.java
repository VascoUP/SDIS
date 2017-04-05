package service.restore;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import file.HandleFile;
import information.Chunk;
import information.FileInfo;
import information.PeerInfo;
import message.MessageInfoGetChunk;
import threads.ThreadManager;

public class Restore {
	private Chunk[] backedupChunks;
	private Chunk[] receivedChunks;
	
	private String filePath;
	private String fileID;

	public Restore(String filePath) throws Exception {
		super();
		
		this.filePath = filePath;
		this.backedupChunks = FileInfo.findAllBackedUpChunks(filePath);
		
		if( backedupChunks.length < 1 )
			throw new Exception("No backed up chunks with this path were found");
		
		this.fileID = backedupChunks[0].getFileId();
	}
	
	
	private void writeReceivedChunks() {
		for( int i = 0; i < receivedChunks.length; i++ ) {
			try {
				HandleFile.writeFile(receivedChunks[i].getChunk(), filePath);
			} catch (IOException e) {
				System.out.println("Error writting to file " + filePath);
			}
		}
	}
	
	
	public void addReceivedChunk(Chunk chunk) {
		Lock lock = new ReentrantLock();
		lock.lock();
		try {
			receivedChunks[chunk.getChunkId()] = chunk;
		} finally {
			lock.unlock();
		}
	}
	
	public void handleReceivedChunks() {
		for( int i = 0; i < receivedChunks.length; i++ ) {
			if( receivedChunks[i] == null ) {
				System.out.println("Null at " + i);
				return ;
			}
			System.out.println(receivedChunks[i].getFileId() + " - " + receivedChunks[i].getChunkId());
		}
		
		writeReceivedChunks();
	}
	
	
	public void run_service() {		
		if( fileID == null )
			return;
		
		for( int i = 0; i < backedupChunks.length; i++ ) {
			ThreadManager.initRestore(
					new MessageInfoGetChunk(
							PeerInfo.peerInfo.getVersionProtocol(), 
							PeerInfo.peerInfo.getServerID(),
							fileID, 
							i));
		}
		
		handleReceivedChunks();
	}
}
