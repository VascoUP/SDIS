package information;

import java.util.HashMap;

public class MessagesHashmap {
	private static HashMap<String, Integer> messages = new HashMap<String, Integer>();
	
	public static synchronized void addMessage(String key) {
		Integer i;
		if( (i = messages.get(key)) != null )
			messages.put(key, ++i);
		else
			messages.put(key, 1);
	}
	
	public static synchronized int getValue(String key) {
		Integer i = messages.get(key);
		if( i == null )
			i = -1;
		return i;
	}
	
	public static synchronized void removeKey(String key) {
		messages.remove(key);
	}
}
