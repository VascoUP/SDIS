package service.general;

import information.PeerInfo;
import message.BasicMessage;
import message.MessageConst;

class MessageToService {
	public static void processMessage(long time, BasicMessage message) {
		String[] head = message.getHead();
		if( head.length < 3 )
			return ;
		
		String messageType = head[0];
		String versionProtocol = head[1];
		int senderID = Integer.parseInt(head[2]);
		
		if( senderID == PeerInfo.peerInfo.getServerID() ||
			!versionProtocol.equals(PeerInfo.peerInfo.getVersionProtocol()) ) {
			System.out.println("ProcessMessage: Same execution message");
			return ;
		}

		switch(messageType) {
		case MessageConst.PUTCHUNK_MESSAGE_TYPE:
			WaitStoreChunk.serve(time, message);
			break;
		case MessageConst.STORED_MESSAGE_TYPE:
			MessageServiceStore.serve(time, message);
			break;
		case MessageConst.RESTORE_MESSAGE_TYPE:
			/*
			 * Service -> GetChunk
			 * 		1st: Wait random time
			 * 		2nd: Check and see if there were already answers 
			 * 				If not
			 * 					Get requested chunk
			 * 					Send, to the MDR channel, the chunk message
			 * 				Else
			 * 					Ignore and return right away
			 */
			break;
		case MessageConst.CHUNK_MESSAGE_TYPE:
			/*
			 * Service -> Add element to a blocking queue(? probably)
			 * 		1st: Find a way to wake the waiting thread 
			 */
			break;
		}
	}
}
