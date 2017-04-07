package protocol;

import java.io.IOException;

import file.HandleFile;
import information.Chunk;
import information.FileInfo;
import information.PeerInfo;
import message.MessageInfoDelete;
import sender.DeleteSender;

public class Delete implements Protocol {
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

	@Override
	public void initialize_sender() throws IOException {
		DeleteSender sender = new DeleteSender(
				new MessageInfoDelete(
						PeerInfo.peerInfo.getVersionProtocol(), 
						PeerInfo.peerInfo.getServerID(),
						fileID));
		sender.execute();
	}
	
	@Override
	public void run_service() {
		try {
			initialize_sender();
		} catch (IOException ignore) {
		}
		
		HandleFile.deleteFile(filePath);
	}
}
