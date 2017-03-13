package protocol;

import java.io.IOException;
import connection.SLMCast;

public class Protocol {

	protected SLMCast mc;
	
	public Protocol(String addr, int port) throws IOException {
		mc = new SLMCast(addr, port);
	}
}
