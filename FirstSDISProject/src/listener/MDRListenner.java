package listener;

import java.io.IOException;

import connection.ConnectionConstants;

public class MDRListenner extends ChannelListener {
	public MDRListenner() throws IOException {
		super(ConnectionConstants.MDR_GROUP, ConnectionConstants.MDR_GROUP_PORT);
	}
}
