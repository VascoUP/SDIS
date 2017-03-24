package service.restore;

import java.io.IOException;

import information.Storable;
import protocol.restore.SendChunk;
import service.general.Service;

public class WaitRestore extends Service implements Storable {
	
	public WaitRestore() throws IOException {
		super();
		
		protocol = new SendChunk();
	}
	
	protected void run_continuous_service() throws IOException, InterruptedException  {
		String rcv = "";
	
		rcv = protocol.receive();
		System.out.println("Rcv: " + rcv);
		
		randomWait();
		
		//sc.setMessage(1, 1);
		protocol.send();
	}

	@Override
	public void run() {
		run_continuous();
	}
}
