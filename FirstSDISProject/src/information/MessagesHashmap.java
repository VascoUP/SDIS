package information;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class MessagesHashmap {
	private static HashMap<String, Integer> messages = new HashMap<String, Integer>();
	
	public static void addMessage(String key) {
		Lock lock = new ReentrantLock();
		lock.lock();
		try {
			Integer i;
			if( (i = messages.get(key)) != null )
				messages.put(key, ++i);
			else
				messages.put(key, 1);
		} finally {
			lock.unlock();
		}
	}
	
	public static int getValue(String key) {
		Integer i;
		Lock lock = new ReentrantLock();
		lock.lock();
		try {
			i = messages.get(key);
			if( i == null )
				i = -1;
		} finally {
			lock.unlock();
		}
		
		return i;
	}
	
	public static void removeKey(String key) {
		Lock lock = new ReentrantLock();
		lock.lock();
		try {
			messages.remove(key);
		} finally {
			lock.unlock();
		}
	}
}
