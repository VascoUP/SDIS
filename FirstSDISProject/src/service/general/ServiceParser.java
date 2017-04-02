package service.general;

import information.MessageQueue;
import message.general.BasicMessage;
import message.general.MessageParser;

public class ServiceParser {	
	public static void parseService() {
		byte[] message = getNextQueuedMessage();
		if( message == null ) {
			//System.out.println("ServiceParser: Null message -> Error getting message");
			return ;
		}
		parseMessage(message);
	}
	
	public static byte[] getNextQueuedMessage() {
		try {
			return MessageQueue.getMessageQueue().take();
		} catch (InterruptedException e) {
			return null;
		}
	}
	
	public static void parseMessage(byte[] message) {
		BasicMessage bM = MessageParser.parseMessage(message);
		String[] bMHead;
		
		if( bM != null && (bMHead = bM.getHead()) != null ) {
			for( int i = 0; i < bMHead.length; i++ )
				System.out.print(bMHead[i] + " ");
			System.out.println("End message");
		} else
			System.out.println("ServiceParser: Null message -> Error parsing");		
	}
}
