package service.restore;

import java.io.IOException;

import information.Storable;
import message.general.Message;
import protocol.restore.SendChunk;
import service.general.ContinuousService;

public class WaitRestore extends ContinuousService implements Storable {
	
	public WaitRestore() throws IOException {
		super();
		
		protocol = new SendChunk();
	}
	
	public void run_service() throws IOException, InterruptedException  {
		byte[] rcv;
	
		rcv = protocol.receive();
		Message.printByteArray(rcv);
		
		randomWait();
		
		protocol.send();
	}
}
