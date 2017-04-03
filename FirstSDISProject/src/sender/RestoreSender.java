package sender;

import java.io.IOException;

import connection.ConnectionConstants;
import message.MessageInfoGetChunk;

public class RestoreSender extends ChannelSender {
	public RestoreSender(MessageInfoGetChunk message) throws IOException {
		super( message, ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
	}
}
