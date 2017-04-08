package sender;

import java.io.IOException;

import connection.ConnectionConstants;
import message.MessageInfoRemoved;

/**
 * 
 * This class builds a removed sender that extends a channel sender class
 *
 */
public class RemovedSender extends ChannelSender {

	/**
	 * RemovedSender's constructor
	 * @param message MessageInfoRemoved to be used by the channel sender
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	*/
	public RemovedSender(MessageInfoRemoved message) throws IOException {
		super(message, ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT);
	}

}
