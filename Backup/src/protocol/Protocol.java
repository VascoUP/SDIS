package protocol;

import java.io.IOException;

/**
 * 
 * Protocol interface
 *
 */
public interface Protocol {
	/**
	 * Initializes the protocol's sender
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	void initialize_sender() throws IOException;
	/**
	 * Runs the protocol's service
	 */
	void run_service();
}
