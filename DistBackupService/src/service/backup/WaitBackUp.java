package service.backup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import information.Storable;
import protocol.backup.AnswerBackUp;

public class WaitBackUp implements Runnable, Storable {

	private AnswerBackUp abu;
	
	public WaitBackUp() throws IOException {
		abu = new AnswerBackUp();
	}
	
	private void randomWait() throws InterruptedException {
		Random r = new Random();
		int wait = r.nextInt(400);
		TimeUnit.MILLISECONDS.sleep(wait);
	}
	
	public void backup_file() throws IOException, InterruptedException  {
		String rcv = "";
	
		rcv = abu.receive();
		System.out.println("Rcv: " + rcv);
		
		ArrayList<Integer> rcvAttr = getAttributesRCV(rcv);
		randomWait();
		
		System.out.println(rcvAttr.get(0));
		System.out.println(rcvAttr.get(1));
		
		abu.setMessage(rcvAttr.get(0), rcvAttr.get(1));
		abu.send();
	}
	
	public void end() throws IOException {
		abu.close();
	}

	@Override
	public void run() {
		while( true ) {
			try {
				backup_file();
			} catch (IOException | InterruptedException e) {
				return ;
			}
		}
	}
	
	public ArrayList<Integer> getAttributesRCV(String rcv){
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		int i = 0;
		
		while (rcv.charAt(i) != '\n')
			i++;
		
		String idFile = Character.toString(rcv.charAt(i--));
		String idSender = Character.toString(rcv.charAt(i-2));
		
		int fileID = Integer.parseInt(idFile);
		int senderID = Integer.parseInt(idSender);
		
		result.add(senderID);
		result.add(fileID);
		
		return result;
	}
}
