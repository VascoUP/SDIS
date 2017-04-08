package information;

import java.util.HashMap;
import java.util.HashSet;

import message.BasicMessage;
import message.MessageToString;

public class MessagesHashmap {
	private static HashMap<String, HashSet<BasicMessage>> messages = new HashMap<>();

	public static synchronized boolean addMessage(BasicMessage message) {
		String key = MessageToString.getName(message);
		HashSet<BasicMessage> keyMessages;
		
		if( (keyMessages = messages.get(key)) != null )
			return keyMessages.add(message);

		keyMessages = new HashSet<BasicMessage>();
		keyMessages.add(message);
		messages.put(key, keyMessages);
		
		return true;
	}

	public static synchronized int getSize(BasicMessage message) {
		String key = MessageToString.getName(message);
		HashSet<BasicMessage> keyMessages = messages.get(key);
		return keyMessages == null ? 0 : keyMessages.size();
	}

	public static synchronized void removeKey(BasicMessage message) {
		String key = MessageToString.getName(message);
		messages.remove(key);
	}

	public static synchronized BasicMessage searchKey(BasicMessage message) {
		String key = MessageToString.getName(message);
		HashSet<BasicMessage> keyMessages = messages.get(key);
		if( keyMessages == null )
			return null;
		for( BasicMessage wanted : keyMessages )
			return wanted;
		return null;
	}
}