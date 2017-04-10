package workerHandlers;

import information.MessagesHashmap;
import information.PeerInfo;
import information.Version;
import message.BasicMessage;
import message.MessageConst;

/**
 * 
 * This class converts a message to a service
 *
 */
public class MessageToService {
	/**
	 * Processes a basic message
	 * @param time Message's time
	 * @param message Basic message that will be processed
	 */
	public static void processMessage(long time, BasicMessage message) {
		String[] head = message.getHead();
		if( head.length < 3 )
			return ;
		
		String messageType = head[0];
		String versionProtocol = head[1];
		int senderID = Integer.parseInt(head[2]);
		
		if( senderID == PeerInfo.peerInfo.getServerID() ||
			!versionProtocol.equals(Version.instance.getVersionProtocol()) )
			return ;
		
		MessagesHashmap.addMessage(message);

		switch(messageType) {
		case MessageConst.PUTCHUNK_MESSAGE_TYPE:
			WaitStoreChunk.serve(time, message);
			break;
		case MessageConst.STORED_MESSAGE_TYPE:
			MessageServiceStored.serve(time, message);
			break;
		case MessageConst.RESTORE_MESSAGE_TYPE:
			WaitGetChunk.serve(time, message);
			break;
		case MessageConst.CHUNK_MESSAGE_TYPE:
			//MessageServiceChunk.serve(time, message);
			break;
		case MessageConst.DELETE_MESSAGE_TYPE:
			MessageServiceDelete.serve(time, message);
			break;
		case MessageConst.REMOVED_MESSAGE_TYPE:
			WaitPutChunk.serve(time, message);
			break;
		}
	}
}