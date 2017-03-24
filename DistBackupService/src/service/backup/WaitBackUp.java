package service.backup;

import java.io.FileOutputStream;
import java.io.IOException;

import information.Storable;
import protocol.backup.AnswerBackUp;
import service.general.Service;

public class WaitBackUp extends Service implements Storable {
	
	public WaitBackUp() throws IOException {
		super();
		
		protocol = new AnswerBackUp();
	}
	
	protected void run_continuous_service() throws IOException, InterruptedException  {
		AnswerBackUp abu = (AnswerBackUp) protocol;
		String rcv = "", fileName = "";
		FileOutputStream output;
		int fileID, chunkID;
		String[] values;
		byte[] data;
	
		rcv = abu.receive();
		System.out.println("Rcv: " + rcv);
		
		randomWait();
		
		values = getAttributesRCV(rcv);
		
		fileID = Integer.parseInt(values[3]);
		chunkID = Integer.parseInt(values[4]);
		
		data = rcv.getBytes();
		
		fileName = "Chunk " + chunkID;
		
		output = new FileOutputStream(fileName);
		output.write(data);
		
		output.close();
		
		abu.setMessage(fileID, chunkID);
		abu.send();
	}

	@Override
	public void run() {
		run_continuous();
	}
	
	public String[] getAttributesRCV(String rcv){
		String[] tmp = rcv.split("\n");
		String[] result = tmp[0].split(" ");
		
		return result;
	}
}

