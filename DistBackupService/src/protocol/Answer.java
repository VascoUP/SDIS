package protocol;

import java.io.IOException;
import java.net.SocketTimeoutException;

public interface Answer {
	public void send() throws IOException;
	public String receive() throws SocketTimeoutException, IOException;
}
