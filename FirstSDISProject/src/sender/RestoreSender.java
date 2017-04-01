package sender;

import java.io.IOException;

import connection.ConnectionConstants;
import message.restore.GetChunkMessage;

public class RestoreSender extends ChannelSender {
	public RestoreSender(GetChunkMessage message) throws IOException {
		super( message, ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
	}
}
