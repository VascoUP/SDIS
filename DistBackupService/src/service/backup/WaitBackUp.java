package service.backup;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
	
	public void backup_file() throws IOException, InterruptedException, FileNotFoundException  {
		String rcv = "";
	
		rcv = abu.receive();
		System.out.println("Rcv: " + rcv);
		
		randomWait();
		
		String[] values = getAttributesRCV(rcv);
		
		int fileID = Integer.parseInt(values[3]);
		int chunkID = Integer.parseInt(values[4]);
		
		byte[] data = rcv.getBytes();
		
		String fileName = "Chunk " + chunkID;
		
		FileOutputStream output = new FileOutputStream(fileName);
		output.write(data);
		
		output.close();
		
		abu.setMessage(fileID, chunkID);
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
	
	public String[] getAttributesRCV(String rcv){
		
		String[] tmp = rcv.split("\n");
		String tmpSplit = tmp.toString();
		String[] result = tmpSplit.split(" ");
		
		return result;
	}
}
