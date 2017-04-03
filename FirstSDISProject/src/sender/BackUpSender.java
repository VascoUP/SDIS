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
	
	private void cooldown(long ms) {
	    try {
	       long waitUntilMillis = System.currentTimeMillis() + ms;
	       long waitTimeMillis = ms;
	       do {
	          this.wait(waitTimeMillis);
	          // we need this dance/loop because of spurious wakeups, thanks @loki
	          waitTimeMillis = waitUntilMillis - System.currentTimeMillis();
	       } while (waitTimeMillis > 0);
	    } catch (InterruptedException e) {
	    }
	}
	
	public boolean condition() {
		MessageInfoPutChunk m = (MessageInfoPutChunk) message;
		String name = MessageToString.getName(m);
		return MessagesHashmap.getValue(name) >= m.getReplication_degree();
	}
	
	public void execute() {
		do {
			sendMessage();
			cooldown(1000);
		} while( !condition() );
		
		System.out.println("Yey");
	}
}
