package sender;

import java.io.IOException;

import connection.ConnectionConstants;
import message.MessageInfoRemoved;

public class RemovedSender extends ChannelSender {

	public RemovedSender(MessageInfoRemoved message) throws IOException {
		super(message, ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT);
	}

}
