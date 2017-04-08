package sender;

import java.io.IOException;

import connection.ConnectionConstants;
import message.MessageInfoStored;

/**
 * 
 * This class builds a answer backup sender
 * This extends the channel sender class
 *
 */
public class AnswerBackUpSender extends ChannelSender {
	/**
	 * AnswerBackUpSender's constructor
	 * @param message Stored message
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	public AnswerBackUpSender(MessageInfoStored message) throws IOException {
		super(message, ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT);
	}
}
