package service.general;

import message.*;

class WaitStoreChunk extends MessageServiceWait {

	private WaitStoreChunk(long time, BasicMessage message) {
		super(time, message);
	}


    public static void serve(long time, BasicMessage message) {
		WaitStoreChunk wsc = new WaitStoreChunk(time, message);
		wsc.start();
	}
}
