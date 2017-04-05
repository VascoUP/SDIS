package listener;

import connection.ConnectionConstants;

import java.io.IOException;

public class MCListenner extends ChannelListener {
	public MCListenner() throws IOException {
		super(ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT);
	}
}
