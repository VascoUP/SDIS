package workerHandlers;

import java.io.IOException;
import java.util.Arrays;

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
	private int prepdeg = -1;
	
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
		MessageInfoStored m1 = new MessageInfoStored(
									PeerInfo.peerInfo.getVersionProtocol(), 
									PeerInfo.peerInfo.getServerID(), 
									backupMessage.getFileID(), 
									backupMessage.getChunkID());
		BasicMessage m2 = InfoToMessage.toMessage(m1);
		if( m2 != null )
			prepdeg = MessagesHashmap.getSize(m2);
	}
	
	private boolean equalChunks() {
		String pathName = HandleFile.getFileName(info.getFileID(), info.getChunkID());
		byte[] chunk;
		try {
			chunk = HandleFile.readFile(pathName);
		} catch (IOException e) {
			return false;
		}
		
		return chunk != null && Arrays.equals(chunk, info.getChunk());		
	}
	
	@Override
	public boolean condition() {
		getValue();
		return 	(	info != null && 
					prepdeg < info.getReplicationDegree()
				) ||
				(	FileInfo.findStoredChunk(info.getFileID(), info.getChunkID()) != null &&
					equalChunks()
				);
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
