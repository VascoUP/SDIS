package sender;

import java.io.IOException;

import connection.ConnectionConstants;
import message.MessageInfoDelete;

/**
 * 
 * This class builds a delete sender that extends a channel sender class
 *
 */
public class DeleteSender extends ChannelSender {
	/**
	 * DeleteSender's constructor
	 * @param message MessageInfoDelete to be used by the channel sender
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	public DeleteSender(MessageInfoDelete message) throws IOException {
		super( message, ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT);
	}
}
