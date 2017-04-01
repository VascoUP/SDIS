package service.general;

public abstract class ContinuousService {
/*	public ContinuousService() {
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
		System.out.println(rcv);
		if( rcv != null )
			handle_message(rcv);
	}
	
	@Override
	public void run() {
		while( !Thread.interrupted() ) {			
			try {
				run_service();
			} catch (IOException | InterruptedException e) {
				return ;
			}
		}
	}
*/
}
