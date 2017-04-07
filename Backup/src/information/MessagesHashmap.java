package information;

import java.util.HashMap;

import message.BasicMessage;

public class MessagesHashmap {
	private static HashMap<BasicMessage, Integer> messages = new HashMap<>();
	
	public static synchronized void addMessage(BasicMessage key) {
		Integer i;
		if( (i = messages.get(key)) != null )
			messages.put(key, ++i);
		else
			messages.put(key, 1);
	}
	
	public static synchronized int getValue(BasicMessage key) {
		Integer i = messages.get(key);
		return i == null ? 0 : i;
	}
	
	public static synchronized void removeKey(BasicMessage key) {
		messages.remove(key);
	}
	
	public static synchronized BasicMessage searchKey(BasicMessage key) {
		for( BasicMessage k : messages.keySet() )
			if( k.equals(key) )
				return k;
		return null;
	}
}
