package service.restore;

import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import file.HandleFile;
import information.Chunk;
import information.FileInfo;
import information.PeerInfo;
import message.MessageInfoGetChunk;
import sender.RestoreSender;
import threads.ThreadManager;

public class Restore {
	private final Lock lock = new ReentrantLock();
	private final Condition lastChunk  = lock.newCondition(); 
	   
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
		
		this.receivedChunks = new Chunk[backedupChunks.length];
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
	
	private boolean allChunks() {
		for( Chunk receivedChunk : receivedChunks )
			if( receivedChunk == null )
				return false;
		return true;
	}
	
	private void getReceivedChunks() throws InterruptedException {
		System.out.println("Restore: getReceivedChunks");
		lock.lock();
		try {
			while( !allChunks() ) {
				System.out.println("Restore: await");
				lastChunk.await();
			}
		} finally {
			lock.unlock();
		}
	}
	
	private void handleReceivedChunks() {
		for( int i = 0; i < receivedChunks.length; i++ ) {
			if( receivedChunks[i] == null ) {
				System.out.println("Null at " + i);
				return ;
			}
			System.out.println(receivedChunks[i].getFileId() + " - " + receivedChunks[i].getChunkId());
		}
		
		writeReceivedChunks();
	}

	public void addReceivedChunk(Chunk chunk) {
		lock.lock();
		try {
			receivedChunks[chunk.getChunkId()] = chunk;
			if( allChunks() ) {
				System.out.println("Restore: Signal");
				lastChunk.signal();
			}
		} finally {
			lock.unlock();
		}
	}
	
	public void run_service() {
		if( fileID == null )
			return;
		
		for( int i = 0; i < backedupChunks.length; i++ ) {
			try {
				ThreadManager.initRestore(
						new RestoreSender(
							this,
							new MessageInfoGetChunk(
									PeerInfo.peerInfo.getVersionProtocol(), 
									PeerInfo.peerInfo.getServerID(),
									fileID, 
									i)));
			} catch (IOException e) {
				System.out.println("Restore: IOException error");
				return ;
			}
		}
		
		try {
			getReceivedChunks();
		} catch (InterruptedException e) {
			return ;
		}
		
		handleReceivedChunks();
	}
}
