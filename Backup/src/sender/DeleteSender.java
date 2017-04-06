package sender;

import java.io.IOException;

import connection.ConnectionConstants;
import message.MessageInfoPutChunk;

public class DeleteSender extends ChannelSender {
	public DeleteSender(MessageInfoPutChunk message) throws IOException {
		super( message, ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT);
	}
}
