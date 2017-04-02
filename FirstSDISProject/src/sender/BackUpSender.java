package sender;

import java.io.IOException;

import connection.ConnectionConstants;
import message.backup.BackUpMessage;

public class BackUpSender extends ChannelSender {
	public BackUpSender(BackUpMessage message) throws IOException {
		super( message, ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
	}
}
