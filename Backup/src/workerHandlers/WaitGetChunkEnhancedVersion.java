package workerHandlers;

import information.MessagesHashmap;
import information.PeerInfo;
import information.Version;
import message.BasicMessage;
import message.InfoToMessage;
import message.MessageInfoChunk;
import message.MessageInfoGetChunk;

/**
 * 
 * This class provides a handler that waits to get a chunk (this is a enhanced version)
 * This extends the WaitGetChunk class
 *
 */
public class WaitGetChunkEnhancedVersion extends WaitGetChunk {

	/**
	 * WaitGetChunkEnhancedVersion's constructor
	 * @param time Service's time
	 * @param message Basic message
	 */
	public WaitGetChunkEnhancedVersion(long time, BasicMessage message) {
		super(time, message);
	}
	
	/**
	 * Verifies if the MessageInfoGetChunk and the basic message created aren't null and if the hashmap size is less than 1
	 * @return true if the condition is verified, false otherwise
	 */
	@Override
	public boolean condition() {				
		MessageInfoGetChunk restoreMessage = (MessageInfoGetChunk) info;
		MessageInfoChunk m1 = new MessageInfoChunk(
								Version.instance.getVersionProtocol(),
								PeerInfo.peerInfo.getServerID(), 
								restoreMessage.getFileID(), 
								restoreMessage.getChunkID(),
								new byte[0]);
		BasicMessage m2 = InfoToMessage.toMessage(m1);
		
		return 	info != null && m2 != null &&
				MessagesHashmap.getSize(m2) < 1;
	}
}
