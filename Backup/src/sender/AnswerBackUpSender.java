package sender;

import java.io.IOException;

import connection.ConnectionConstants;
import message.MessageInfoStored;

public class AnswerBackUpSender extends ChannelSender {
	public AnswerBackUpSender(MessageInfoStored message) throws IOException {
		super(message, ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT);
	}
}
