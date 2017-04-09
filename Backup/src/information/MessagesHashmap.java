package information;

import java.util.HashMap;
import java.util.HashSet;

import message.BasicMessage;
import message.MessageToString;

/**
 * 
 * This class builds a hash map with messages
 *
 */
public class MessagesHashmap {
	private static HashMap<String, HashSet<BasicMessage>> messages = new HashMap<>(); //HasMap with a HashSet of messages searched by a string key

	/**
	 * Adds a message to the HashMap
	 * @param message New message to be added
	 * @return true if it's possible to add the message or false if the set already contains the element
	 */
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

	/**
	 * Gets the message's size
	 * @param message Message that will be analyzed
	 * @return The message's size
	 */
	public static synchronized int getSize(BasicMessage message) {
		String key = MessageToString.getName(message);
		HashSet<BasicMessage> keyMessages = messages.get(key);
		return keyMessages == null ? 0 : keyMessages.size();
	}

	/**
	 * Removes the string key from HashMap
	 * @param message Message that is associated to the key that will be removed
	 */
	public static synchronized void removeKey(BasicMessage message) {
		String key = MessageToString.getName(message);
		messages.remove(key);
	}

	/**
	 * Searches the key's message
	 * @param message Basic message used to create the string key
	 * @return The key's message
	 */
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