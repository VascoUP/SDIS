package workerHandlers;

import java.io.IOException;

import file.HandleFile;
import information.Chunk;
import information.ChunkStored;
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
	private int prepdeg;
	
	public WaitStoreChunk(long time, BasicMessage message) {
		super(time, message);
	}
	
	private void initInfo() {
		if( info == null )
			info = (MessageInfoPutChunk) MessageToInfo.messageToInfo(message);
	}
	
	private void getValue() {
		initInfo();
		
		MessageInfoPutChunk backupMessage = (MessageInfoPutChunk) info;
		MessageInfoStored m = new MessageInfoStored(
									PeerInfo.peerInfo.getVersionProtocol(), 
									PeerInfo.peerInfo.getServerID(), 
									backupMessage.getFileID(), 
									backupMessage.getChunkID());
		prepdeg = MessagesHashmap.getValue(InfoToMessage.toMessage(m));
	}
	
	@Override
	public boolean condition() {
		getValue();
		return 	info != null && 
				prepdeg < info.getReplicationDegree();
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
		
		chunk = new ChunkStored(fileName, fileID, chunkID, prepdeg + 1, info.getChunk());
		try {
			AnswerBackUpSender abup = new AnswerBackUpSender(
					new MessageInfoStored(
						PeerInfo.peerInfo.getVersionProtocol(),
						PeerInfo.peerInfo.getServerID(),
						fileID, 
						chunkID));
			abup.execute();
			FileInfo.addStoredChunk((ChunkStored)chunk);
			HandleFile.writeFile(info.getChunk(), fileName);
		} catch (IOException ignore) {
		}
	}
	

	public static void serve(long time, BasicMessage message) {
		WaitStoreChunk wsc = new WaitStoreChunk(time, message);
		wsc.start();
	}
}
