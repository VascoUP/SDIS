package sender;

import java.io.IOException;

import connection.ConnectionConstants;
import message.MessageInfoChunk;

/**
 * 
 * This class builds a answer backup sender
 * This extends the channel sender class
 */
public class AnswerRestoreSender extends ChannelSender {
	/**
	 * AnswerRestoreSender's constructor
	 * @param message Chunk's information
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	public AnswerRestoreSender(MessageInfoChunk message) throws IOException {
		super(message, ConnectionConstants.MDR_GROUP, ConnectionConstants.MDR_GROUP_PORT);
	}
}
