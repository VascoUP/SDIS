package service;

import java.io.IOException;

import file.HandleFile;
import information.Chunk;
import information.FileInfo;
import information.PeerInfo;
import message.MessageInfoPutChunk;
import sender.DeleteSender;

public class Delete {
	private String filePath;
	private String fileID;

	public Delete(String filePath) {
		super();
		
		this.filePath = filePath;
		Chunk[] chunks = FileInfo.findAllBackedUpChunks(filePath);
		if( chunks.length < 0 )
			return ;
		
		this.fileID = chunks[0].getFileId();
	}
		
	public void run_service() {
		try {
			DeleteSender sender = new DeleteSender(
				new MessageInfoPutChunk(
						PeerInfo.peerInfo.getVersionProtocol(), 
						PeerInfo.peerInfo.getServerID(),
						fileID, 
						1,
						1, 
						null));
			sender.execute();
		} catch (IOException ignore) {
		}
		
		HandleFile.deleteFile(filePath);
	}
}
