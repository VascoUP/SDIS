package sender;

import java.io.IOException;

import connection.ConnectionConstants;
import message.restore.ChunkMessage;

public class AnswerRestoreSender extends ChannelSender {
	public AnswerRestoreSender(ChunkMessage message) throws IOException {
		super(message, ConnectionConstants.MDR_GROUP, ConnectionConstants.MDR_GROUP_PORT);
	}
}
