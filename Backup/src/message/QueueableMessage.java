package message;

public class QueueableMessage {
	private final long time;
	private final byte[] data;
	
	public QueueableMessage(long time, byte[] data) {
		this.time = time;
		this.data = data;
	}
	
	public byte[] getData() {
		return data;
	}
	
	public long getTime() {
		return time;
	}
}
