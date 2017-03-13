package protocol;

import java.io.IOException;
import java.net.SocketTimeoutException;

public interface Request {
	public void send() throws IOException;
	public String receive() throws SocketTimeoutException, IOException;
}
