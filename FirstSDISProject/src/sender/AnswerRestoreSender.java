package sender;

import java.io.IOException;

import connection.ConnectionConstants;
import message.MessageInfoChunk;

public class AnswerRestoreSender extends ChannelSender {
	public AnswerRestoreSender(MessageInfoChunk message) throws IOException {
		super(message, ConnectionConstants.MDR_GROUP, ConnectionConstants.MDR_GROUP_PORT);
	}
}
