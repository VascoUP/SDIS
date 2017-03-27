package service.general;

import java.io.IOException;

public abstract class PontualService extends Service {
	public PontualService() {
		super();
	}

	public int wait_answers() {
		int i = 0;
		
		long t = System.currentTimeMillis();
		long end = t + 1000;

		byte[] rcv;
		
		while((t = end - System.currentTimeMillis()) > 0 ){
			protocol.socketTimeout((int)t);
			
			try {
				rcv = protocol.receive();
			} catch (IOException e) {
				break;
			}
			
			if(validateMessage(rcv))
				i++;
		}
		
		return i;
	}
	
	
	public void run_service() {
		try {
			protocol.send();
		} catch (IOException e) {
			System.out.println("Error sending chunk");
			return ;
		}
		
		wait_answers();
	}
		
	@Override
	public void run() {
		run_service();
		end_service();
	}
}
