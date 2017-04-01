package listener;

import java.io.IOException;

import connection.ConnectionConstants;

public class MCListenner extends ChannelListener {
	public MCListenner() throws IOException {
		super(ConnectionConstants.MC_GROUP, ConnectionConstants.MC_GROUP_PORT);
	}
}
