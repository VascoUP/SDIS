package message;

public class QueueableMessage {
	private long time;
	private byte[] data;
	
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
