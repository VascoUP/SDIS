package message;

public class MessageConst {
	public static final String PUTCHUNK_MESSAGE_TYPE = "PUTCHUNK";			//PutChunk message's type
	public static final int PUTCHUNK_MESSAGE_LENGTH = 6;					//PutChunk message's size
	public static final String STORED_MESSAGE_TYPE = "STORED";				//Stored message's type
	public static final int STORED_MESSAGE_LENGTH = 5;						//Stored message's size
	public static final String RESTORE_MESSAGE_TYPE = "GETCHUNK";			//GetChunk message's type
	public static final int RESTORE_MESSAGE_LENGTH = 5;						//GetChunk message's size
	public static final String CHUNK_MESSAGE_TYPE = "CHUNK";				//Chunk message's type
	public static final int CHUNK_MESSAGE_LENGTH = 6;						//Chunk message's size
	public static final String DELETE_MESSAGE_TYPE = "DELETE";				//Delete message's type
	public static final int DELETE_MESSAGE_LENGTH = 4;						//Delete message's size
	public static final String REMOVED_MESSAGE_TYPE = "REMOVED";			//Removed message's type
	public static final int REMOVED_MESSAGE_LENGTH = 5;						//Removed message's size
	
	public static final byte[] MESSAGE_FLAG = {0xD, 0xA};					//Message's flag
	public static final int CHUNKSIZE = 64000;								//Chunk's size
	public static final String MESSAGE_ERROR = "Message is not valid";		//Error's message
	
	public static final int STORE_TYPE_HEAD = 1;							//Header
	public static final int STORE_TYPE_BODY = 2;							//Body
}
