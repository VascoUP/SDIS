package message;

/**
 * 
 * This class creates a queueable message
 *
 */
public class QueueableMessage {
	private long time;		//Message's time
	private byte[] data;	//Message's content
	
	/**
	 * QueueableMessage's constructor
	 * @param time Message's time
	 * @param data Message's content
	 */
	public QueueableMessage(long time, byte[] data) {
		this.time = time;
		this.data = data;
	}
	
	/**
	 * Gets the message's content
	 * @return The message's content
	 */
	public byte[] getData() {
		return data;
	}
	
	/**
	 * Gets the message's time
	 * @return The message's time
	 */
	public long getTime() {
		return time;
	}
}
