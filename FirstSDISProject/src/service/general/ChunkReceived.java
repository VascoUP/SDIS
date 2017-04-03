package service.general;

import message.BasicMessage;

public class ChunkReceived extends MessageService {
	public ChunkReceived(long time, BasicMessage message) {
		super(time, message);
	}
	
	public static void serve(long time, BasicMessage message) {
	}
}
