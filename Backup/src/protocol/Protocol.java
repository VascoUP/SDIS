package protocol;

import java.io.IOException;

public interface Protocol {
	void initialize_sender() throws IOException;
	void run_service();
}
