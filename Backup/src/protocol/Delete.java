package protocol;

import java.io.IOException;

import file.HandleFile;
import information.Chunk;
import information.FileInfo;
import information.PeerInfo;
import message.MessageInfoDelete;
import sender.DeleteSender;

/**
 * 
 * This class creates the Delete protocol
 * This implements the Protocol interface
 *
 */
public class Delete implements Protocol {
	private String filePath;	//File's pathname
	private String fileID;		//File's ID

	/**
	 * Delete's constructor
	 * @param filePath File's pathname
	 * @throws Exception The class Exception and its subclasses are a form of Throwable that indicates conditions that a reasonable application might want to catch
	 */
	public Delete(String filePath) throws Exception {
		super();
		
		this.filePath = filePath;
		Chunk[] chunks = FileInfo.findAllBackedUpChunks(filePath);
		if( chunks.length < 1 )
			throw new Exception("No chuncks like this are backedup stored");
		
		this.fileID = chunks[0].getFileId();
	}

	/**
	 * Initializes the delete's sender
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	@Override
	public void initialize_sender() throws IOException {
		DeleteSender sender = new DeleteSender(
				new MessageInfoDelete(
						PeerInfo.peerInfo.getVersionProtocol(), 
						PeerInfo.peerInfo.getServerID(),
						fileID));
		sender.execute();
	}
	
	/**
	 * Runs the delete's service
	 */
	@Override
	public void run_service() {
		try {
			initialize_sender();
		} catch (IOException ignore) {
		}
		
		System.out.println("Deleting: " + filePath);
		FileInfo.eliminateBackedUpFile(filePath);
		HandleFile.deleteFile(filePath);
	}
}
