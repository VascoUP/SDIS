package protocol;

import java.io.IOException;
import java.util.Set;

import information.ChunkStored;
import information.PeerInfo;
import information.Version;
import message.MessageInfoRemoved;
import sender.RemovedSender;

/**
 * 
 * This class creates the Removed protocol
 * This implements the Protocol interface
 *
 */
public class Remove implements Protocol {
	Set<ChunkStored> removeChunks;	//Set of removed chunks
	
	/**
	 * Remove's protocol
	 * @param removeChunks Set of removed chunks
	 */
	public Remove(Set<ChunkStored> removeChunks) {
		this.removeChunks = removeChunks;
	}

	/**
	 * Initializes the removed's sender
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	@Override
	public void initialize_sender() throws IOException {
		for( ChunkStored c : removeChunks ) {
			if( c.getPRepDeg() - 1 < c.getDRepDeg() ) {
				RemovedSender sender = new RemovedSender(
						new MessageInfoRemoved(
								Version.instance.getVersionProtocol(),
								PeerInfo.peerInfo.getServerID(),
								c.getFileId(),
								c.getChunkId()));
				sender.execute();
			}
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
