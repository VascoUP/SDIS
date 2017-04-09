package listener;

import java.io.IOException;

import connection.ConnectionConstants;

/**
 * 
 * This class builds a MC's listener that extends the channel listener class
 *
 */
public class MCListener extends ChannelListener {
	/**
	 * MCListener's constructor
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	public MCListener() throws IOException {
		super(ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT);
	}
}
