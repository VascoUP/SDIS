package service.general;

import java.io.IOException;

public abstract class ContinuousService extends Service {
	public ContinuousService() {
		super();
	}
	
	public boolean checkOtherAnswers(int time) {
		return false;
	}
	
	public boolean sameMessage(byte[] message) {
		return false;
	}

	public boolean handle_message(byte[] message) throws IOException, InterruptedException {
		throw new Error("Handle_message running on the wrong class");
	}
	
	public void run_service() throws IOException, InterruptedException  {
		byte[] rcv = receive();
		if( rcv != null )
			handle_message(rcv);
	}
	
	@Override
	public void run() {
		while( !Thread.interrupted() ) {
			Thread.currentThread().getStackTrace();
			
			try {
				run_service();
			} catch (IOException | InterruptedException e) {
				return ;
			}
		}
	}
}
