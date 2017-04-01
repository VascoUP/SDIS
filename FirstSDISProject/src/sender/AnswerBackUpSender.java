package sender;

import java.io.IOException;

import connection.ConnectionConstants;
import message.backup.StoredMessage;

public class AnswerBackUpSender extends ChannelSender{
	public AnswerBackUpSender(StoredMessage message) throws IOException {
		super(message, ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT);
	}
}
