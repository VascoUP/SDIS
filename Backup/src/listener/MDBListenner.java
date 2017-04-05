package listener;

import java.io.IOException;

import connection.ConnectionConstants;

public class MDBListenner extends ChannelListener {
	public MDBListenner() throws IOException {
		super(ConnectionConstants.MDB_GROUP, ConnectionConstants.MDB_GROUP_PORT);
	}
}
