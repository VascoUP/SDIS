package message.general;

public class MessageConst {
	public static final String STORED_MESSAGE_TYPE = "STORED";
	public static final String PUTCHUNK_MESSAGE_TYPE = "PUTCHUNK";
	public static final String RESTORE_MESSAGE_TYPE = "GETCHUNK";
	public static final String CHUNK_MESSAGE_TYPE = "CHUNK";
	
	public static final byte[] MESSAGE_FLAG = {0xD, 0xA};
	public static final int CHUNKSIZE = 64000;
	public static final String MESSAGE_ERROR = "Message is not valid";
	
	public static final int STORE_TYPE_HEAD = 1;
	public static final int STORE_TYPE_BODY = 2;
}
