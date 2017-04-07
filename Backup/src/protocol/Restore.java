package protocol;

import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import file.HandleFile;
import information.Chunk;
import information.ChunkBackedUp;
import information.ChunkStored;
import information.FileInfo;
import information.PeerInfo;
import message.MessageInfoGetChunk;
import sender.RestoreSender;
import threads.ThreadManager;

public class Restore implements Protocol {
	private final Lock lock = new ReentrantLock();
	private final Condition lastChunk  = lock.newCondition(); 
	   
	private ChunkBackedUp[] backedupChunks;
	private ChunkStored[] receivedChunks;
	
	private String filePath;
	private String fileID;

	public Restore(String filePath) throws Exception {
		super();
		
		this.filePath = filePath;
		this.backedupChunks = FileInfo.findAllBackedUpChunks(filePath);
		
		if( backedupChunks.length < 1 )
			throw new Exception("No backed up chunks with this path were found");
		
		this.receivedChunks = new ChunkStored[backedupChunks.length];
		this.fileID = backedupChunks[0].getFileId();
	}
	
	private void writeReceivedChunks() {
		byte[][] wChunks = new byte[receivedChunks.length][];
		
		for( int i = 0; i < receivedChunks.length; i++ )
			wChunks[i] = receivedChunks[i].getChunk();
		
		try {
			HandleFile.writeFile(wChunks, filePath);
		} catch (IOException e) {
			return ;
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
			if( receivedChunks[i] == null )
				return ;
			System.out.println(receivedChunks[i].getFileId() + " - " + receivedChunks[i].getChunkId());
		}
		
		writeReceivedChunks();
	}

	public void addReceivedChunk(ChunkStored chunk) {
		lock.lock();
		try {
			receivedChunks[chunk.getChunkId() - 1] = chunk;
			if( allChunks() )
				lastChunk.signal();
		} finally {
			lock.unlock();
		}
	}


	@Override
	public void initialize_sender() throws IOException {
		for( int i = 0; i < backedupChunks.length; i++ )
			ThreadManager.initRestore(
				new RestoreSender(
					this,
					new MessageInfoGetChunk(
							PeerInfo.peerInfo.getVersionProtocol(), 
							PeerInfo.peerInfo.getServerID(),
							fileID, 
							i + 1)));
	}
	
	@Override
	public void run_service() {
		if( fileID == null )
			return;
		
		try {
			initialize_sender();
		} catch (IOException e1) {
			return ;
		}
		
		try {
			getReceivedChunks();
		} catch (InterruptedException e) {
			return ;
		}
		
		handleReceivedChunks();
	}
}
