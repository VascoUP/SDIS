package protocol;

import java.io.IOException;
import java.util.Set;

import information.Chunk;
import information.PeerInfo;
import message.MessageInfoRemoved;
import sender.RemovedSender;

/**
 * 
 * This class creates the Removed protocol
 * This implements the Protocol interface
 *
 */
public class Remove implements Protocol {
	Set<Chunk> removeChunks;
	
	public Remove(Set<Chunk> removeChunks) {
		this.removeChunks = removeChunks;
	}

	/**
	 * Initializes the removed's sender
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	@Override
	public void initialize_sender() throws IOException {
		for( Chunk c : removeChunks ) {
			RemovedSender sender = new RemovedSender(
					new MessageInfoRemoved(
							PeerInfo.peerInfo.getVersionProtocol(), 
							PeerInfo.peerInfo.getServerID(),
							c.getFileId(),
							c.getChunkId()));
			sender.execute();
		}
	}

	/**
	 * Runs the removed's service
	 */
	@Override
	public void run_service() {
		try {
			initialize_sender();
		} catch( IOException ignore ) {
		}
	}

}
