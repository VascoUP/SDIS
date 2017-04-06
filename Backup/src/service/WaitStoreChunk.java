package service;

import java.io.IOException;

import file.HandleFile;
import information.Chunk;
import information.FileInfo;
import information.MessagesHashmap;
import information.PeerInfo;
import message.BasicMessage;
import message.InfoToMessage;
import message.MessageInfoPutChunk;
import message.MessageInfoStored;
import message.MessageToInfo;
import sender.AnswerBackUpSender;

public class WaitStoreChunk extends MessageServiceWait {
	private MessageInfoPutChunk info;
	
	public WaitStoreChunk(long time, BasicMessage message) {
		super(time, message);
	}
	
	public void initInfo() {
		if( info == null )
			info = (MessageInfoPutChunk) MessageToInfo.messageToInfo(message);
	}
	
	@Override
	public boolean condition() {
		initInfo();
		
		MessageInfoPutChunk backupMessage = (MessageInfoPutChunk) info;
		MessageInfoStored m = new MessageInfoStored(
									PeerInfo.peerInfo.getVersionProtocol(), 
									PeerInfo.peerInfo.getServerID(), 
									backupMessage.getFileID(), 
									backupMessage.getChunkID());
		
		return 	info != null && 
				MessagesHashmap.getValue(InfoToMessage.toMessage(m)) < info.getReplicationDegree();
	}
	
	@Override
	protected void service() {
		Chunk chunk;
		String fileName, fileID;
		int chunkID;

		initInfo();
		
		fileID = info.getFileID();
		chunkID = info.getChunkID();
		fileName = HandleFile.getFileName(fileID, chunkID);
		
		chunk = new Chunk(fileName, fileID, chunkID, info.getChunk());
		try {
			chunk.store();
			AnswerBackUpSender abup = new AnswerBackUpSender(
					new MessageInfoStored(
						PeerInfo.peerInfo.getVersionProtocol(),
						PeerInfo.peerInfo.getServerID(),
						fileID, 
						chunkID));
			abup.execute();
		} catch (IOException e) {
			return ;
		}
		
		FileInfo.addStoredChunk(chunk);
	}
	

	public static void serve(long time, BasicMessage message) {
		WaitStoreChunk wsc = new WaitStoreChunk(time, message);
		wsc.start();
	}
}
