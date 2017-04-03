package sender;

import java.io.IOException;

import connection.ConnectionConstants;
import message.MessageInfoPutChunk;

public class BackUpSender extends ChannelSender {	
	public BackUpSender(MessageInfoPutChunk message) throws IOException {
		super( message, ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
	}
	
	public boolean condition() {
		return true;
	}
}
