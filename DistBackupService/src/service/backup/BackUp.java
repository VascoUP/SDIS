package service.backup;

import java.io.IOException;

import protocol.backup.RequestBackUp;

public class BackUp {
	//private String file = "";
	private RequestBackUp rbu;
	
	public BackUp() throws IOException {
		rbu = new RequestBackUp();
	}
	
	public void backup_file() throws IOException {
		rbu.send();
		String rcv = rbu.receive();
		System.out.println(rcv);
		
		//If number of rcv is lower than expected, resend the same chunk
		//If not, advance to the next one
	}
}
