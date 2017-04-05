package sender;

import message.MessageInfoGetChunk;

import java.io.IOException;

public class RestoreSender extends ChannelSender {
	public RestoreSender(MessageInfoGetChunk message) throws IOException {
		super( message);
	}
}
