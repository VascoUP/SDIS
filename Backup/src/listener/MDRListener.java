package listener;

import java.io.IOException;

import connection.ConnectionConstants;

/**
 * 
 * This class builds the MDR's listener that extends the channel listener class
 *
 */
public class MDRListener extends ChannelListener {
	/**
	 * MDRListener's constructor
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	public MDRListener() throws IOException {
		super(ConnectionConstants.MDR_GROUP, ConnectionConstants.MDR_GROUP_PORT);
	}
}
