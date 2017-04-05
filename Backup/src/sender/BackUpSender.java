package sender;

import information.Chunk;
import information.FileInfo;
import information.MessagesHashmap;
import information.PeerInfo;
import message.MessageInfoPutChunk;
import message.MessageInfoStored;
import message.MessageToString;

import java.io.IOException;

public class BackUpSender extends ChannelSender {
	private String filePath;
	
	public BackUpSender(String filePath, MessageInfoPutChunk message) throws IOException {
		super( message);
		this.filePath = filePath;
	}
	
	private boolean condition() {
		MessageInfoPutChunk backupMessage = (MessageInfoPutChunk) message;
		MessageInfoStored m = new MessageInfoStored(
									PeerInfo.peerInfo.getVersionProtocol(), 
									PeerInfo.peerInfo.getServerID(), 
									backupMessage.getFileID(), 
									backupMessage.getChunkID());
		String key = MessageToString.getName(m);
		return MessagesHashmap.getValue(key) >= backupMessage.getReplication_degree();
	}
	
	private void cooldown() {
		try {
			long waitUntilMillis = System.currentTimeMillis() + (long) 1000;
			long waitTimeMillis = (long) 1000;
			do {
				Thread.sleep(waitTimeMillis);
				waitTimeMillis = waitUntilMillis - System.currentTimeMillis();
			} while (waitTimeMillis > 0);
		} catch (InterruptedException ignored) {
		}
	}
	
	@Override
	public void execute() {
		do {
			sendMessage();
			cooldown();
		} while( !condition() );
		
		MessagesHashmap.removeKey(MessageToString.getName(message));
		MessageInfoPutChunk backupMessage = (MessageInfoPutChunk) message;
		FileInfo.addBackedUpChunk(new Chunk(filePath, backupMessage.getFileID(), backupMessage.getChunkID()));
		System.out.println("BackUpSender: Yey");
	}
}
