package service.restore;

import java.io.IOException;

import information.Storable;
import message.general.Message;
import protocol.restore.SendChunk;
import service.general.Service;

public class WaitRestore extends Service implements Storable {
	
	public WaitRestore() throws IOException {
		super();
		
		protocol = new SendChunk();
	}
	
	protected void run_continuous_service() throws IOException, InterruptedException  {
		byte[] rcv;
	
		rcv = protocol.receive();
		Message.printByteArray(rcv);
		
		randomWait();
		
		protocol.send();
	}

	@Override
	public void run() {
		run_continuous();
	}
}
