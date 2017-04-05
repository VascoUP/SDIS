package listener;

import connection.ConnectionConstants;

import java.io.IOException;

public class MDBListenner extends ChannelListener {
	public MDBListenner() throws IOException {
		super(ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
	}
}
