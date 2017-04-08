package workerHandlers;

import java.io.IOException;

import file.HandleFile;
import information.MessagesHashmap;
import information.PeerInfo;
import message.BasicMessage;
import message.InfoToMessage;
import message.MessageInfoChunk;
import message.MessageInfoGetChunk;
import message.MessageToInfo;
import sender.AnswerRestoreSender;

public class WaitGetChunk extends MessageServiceWait {
	private MessageInfoGetChunk info;
	
	public WaitGetChunk(long time, BasicMessage message) {
		super(time, message);
	}
	
	public void initInfo() {
		if( info == null )
			info = (MessageInfoGetChunk) MessageToInfo.messageToInfo(message);
	}
	
	@Override
	public boolean condition() {
		initInfo();
				
		MessageInfoGetChunk restoreMessage = (MessageInfoGetChunk) info;
		MessageInfoChunk m1 = new MessageInfoChunk(
								PeerInfo.peerInfo.getVersionProtocol(), 
								PeerInfo.peerInfo.getServerID(), 
								restoreMessage.getFileID(), 
								restoreMessage.getChunkID(),
								new byte[0]);
		BasicMessage m2 = InfoToMessage.toMessage(m1);
		
		return 	info != null && m2 != null &&
				MessagesHashmap.getSize(m2) < 1;
	}
	
	@Override
	public void service() {
		byte[] data;
		String fileName, fileID;
		int chunkID;
		
		initInfo();
				
		fileID = info.getFileID();
		chunkID = info.getChunkID();
		fileName = HandleFile.getFileName(fileID, chunkID);
		
		try {
			data = HandleFile.readFile(fileName);
			if( data == null )
				return ;
			
			AnswerRestoreSender abup = new AnswerRestoreSender(
					new MessageInfoChunk(
						PeerInfo.peerInfo.getVersionProtocol(),
						PeerInfo.peerInfo.getServerID(),
						fileID, 
						chunkID,
						data));
			abup.execute();
		} catch (IOException ignore) {
		}
	}
	
	public static void serve(long time, BasicMessage message) {
		WaitGetChunk gc = new WaitGetChunk(time, message);
		gc.start();
	}
}