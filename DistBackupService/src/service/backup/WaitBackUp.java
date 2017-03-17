package service.backup;

import java.io.IOException;

import protocol.backup.AnswerBackUp;

public class WaitBackUp {

	private AnswerBackUp abu;
	
	public WaitBackUp() throws IOException {
		abu = new AnswerBackUp();
	}
	
	public void backup_file() throws IOException {
		String rcv = abu.receive();
		System.out.println(rcv);
		
		//Check if it can store
		//If not, don't send message
		abu.send();
	}
}
