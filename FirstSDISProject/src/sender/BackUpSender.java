package sender;

import java.io.IOException;

import connection.ConnectionConstants;
import information.MessagesHashmap;
import message.MessageInfoPutChunk;
import message.MessageToString;

public class BackUpSender extends ChannelSender {	
	public BackUpSender(MessageInfoPutChunk message) throws IOException {
		super( message, ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
	}
	
	public boolean condition() {
		MessageInfoPutChunk m = (MessageInfoPutChunk) message;
		String name = MessageToString.getName(m);
		return MessagesHashmap.getValue(name) >= m.getReplication_degree();
	}
	
	private void cooldown(long ms) {
		try {
			long waitUntilMillis = System.currentTimeMillis() + ms;
			long waitTimeMillis = ms;
			do {
				Thread.sleep(waitTimeMillis);
				waitTimeMillis = waitUntilMillis - System.currentTimeMillis();
			} while (waitTimeMillis > 0);
		} catch (InterruptedException e) {
		}
	}
	
	@Override
	public void execute() {
		do {
			sendMessage();
			cooldown(1000);
		} while( !condition() );
		
		System.out.println("BackUpSender: Yey");
	}
}
