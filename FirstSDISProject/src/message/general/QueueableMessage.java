package message.general;

public class QueueableMessage {
	private long time;
	private byte[] data;
	
	public QueueableMessage(long time, byte[] data) {
		this.time = time;
		this.data = data;
	}
	
	public long getTime() {
		return time;
	}
	
	public byte[] getData() {
		return data;
	}
}
