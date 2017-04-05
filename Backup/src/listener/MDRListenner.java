package listener;

import connection.ConnectionConstants;

import java.io.IOException;

public class MDRListenner extends ChannelListener {
	public MDRListenner() throws IOException {
		super(ConnectionConstants.MDR_GROUP, ConnectionConstants.MDR_GROUP_PORT);
	}
}
