package protocol;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import file.HandleFile;
import information.Chunk;
import information.ChunkBackedUp;
import information.ChunkStored;
import information.FileInfo;
import information.PeerInfo;
import information.Version;
import message.MessageInfoGetChunk;
import sender.RestoreSender;
import threads.ThreadManager;

/**
 * 
 * This class creates the Restore protocol
 * This implements the Protocol interface
 *
 */
public class Restore implements Protocol {
	private final Lock lock = new ReentrantLock();				//Creates an instance of ReentrantLock, this is equivalent to using ReentrantLock(false)
	private final Condition lastChunk  = lock.newCondition(); 	//Returns a new Condition instance that is bound to this Lock instance
	   
	private ChunkBackedUp[] backedupChunks;						//Backed up chunks
	private ChunkStored[] receivedChunks;						//Received chunks
	
	private String filePath;									//File's pathname
	private String fileID;										//File's ID

	/**
	 * Restore's constructor
	 * @param filePath File's pathname
	 * @throws Exception The class Exception and its subclasses are a form of Throwable that indicates conditions that a reasonable application might want to catch
	 */
	public Restore(String filePath) throws Exception {
		super();
		
		this.filePath = filePath;
		this.backedupChunks = FileInfo.findAllBackedUpChunks(filePath);
		
		if( backedupChunks.length < 1 )
			throw new Exception("No backed up chunks with this path were found");
		
		this.receivedChunks = new ChunkStored[backedupChunks.length];
		this.fileID = backedupChunks[0].getFileId();
		
		System.out.println("Restore: backedupChunks " + backedupChunks.length);
		System.out.println("Restore: receivedChunks " + receivedChunks.length);
	}
	
	/**
	 * Writes the received chunks to a array of byte's array
	 */
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
	
	/**
	 * Verifies if all the chunks received aren't null
	 * @return true if every chunk isn't null, false otherwise
	 */
	private boolean allChunks() {
		for( Chunk receivedChunk : receivedChunks )
			if( receivedChunk == null )
				return false;
		return true;
	}
	
	/**
	 * Gets the received chunks
	 * @throws InterruptedException Thrown when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity
	 */
	private void getReceivedChunks() throws InterruptedException {
		lock.lock(); //Acquires the lock
		try {
			while( !allChunks() ) {
				lastChunk.await(10, TimeUnit.SECONDS); //Causes the current thread to wait until it is signaled or interrupted, or the specified waiting time elapse
				System.out.println(allChunks() ? "Got all the answers" : "No answers");
				break;
			}
		} finally {
			lock.unlock(); //Releases the lock
		}
	}
	
	/**
	 * Handles the received chunks, writting them
	 */
	private void handleReceivedChunks() {
		for( int i = 0; i < receivedChunks.length; i++ ) {
			if( receivedChunks[i] == null )
				return ;
		}
		
		writeReceivedChunks();
	}

	/**
	 * Adds a stored chunk to the received chunks array
	 * @param chunk Stored chunk to be added to the recieved chunks array
	 */
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

	/**
	 * Initializes the restore's sender
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	@Override
	public void initialize_sender() throws IOException {
		for( int i = 0; i < backedupChunks.length; i++ )
			ThreadManager.initRestore(
				new RestoreSender(
					this,
					new MessageInfoGetChunk(
							Version.instance.getVersionProtocol(), 
							PeerInfo.peerInfo.getServerID(),
							fileID, 
							i + 1)));
	}
	
	/**
	 * Runs the restore's service
	 */
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
		} catch (InterruptedException e2) {
			return ;
		}
		
		handleReceivedChunks();
	}
}
