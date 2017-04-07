package workerHandlers;

import information.PeerInfo;
import message.BasicMessage;
import message.MessageConst;

public class MessageToService {
	public static void processMessage(long time, BasicMessage message) {
		String[] head = message.getHead();
		if( head.length < 3 )
			return ;
		
		String messageType = head[0];
		String versionProtocol = head[1];
		int senderID = Integer.parseInt(head[2]);
		
		if( senderID == PeerInfo.peerInfo.getServerID() ||
			!versionProtocol.equals(PeerInfo.peerInfo.getVersionProtocol()) )
			return ;

		switch(messageType) {
		case MessageConst.PUTCHUNK_MESSAGE_TYPE:
			WaitStoreChunk.serve(time, message);
			break;
		case MessageConst.STORED_MESSAGE_TYPE:
			MessageServiceStore.serve(time, message);
			break;
		case MessageConst.RESTORE_MESSAGE_TYPE:
			System.out.println(MessageConst.RESTORE_MESSAGE_TYPE);
			WaitGetChunk.serve(time, message);
			break;
		case MessageConst.CHUNK_MESSAGE_TYPE:
			MessageServiceChunk.serve(time, message);
			break;
		case MessageConst.DELETE_MESSAGE_TYPE:
			MessageServiceDelete.serve(time, message);
			break;
		}
	}
}