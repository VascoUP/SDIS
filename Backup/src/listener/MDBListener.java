package listener;

import java.io.IOException;

import connection.ConnectionConstants;

/**
 * 
 * This class builds a MDB's listener that extends the channel listener
 *
 */
public class MDBListener extends ChannelListener {
	/**
	 * MDBListener's constructor
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	public MDBListener() throws IOException {
		super(ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
	}
}
