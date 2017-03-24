package service.restore;

import java.io.IOException;

import information.Storable;
import protocol.restore.GetChunk;
import service.general.Service;

public class Restore extends Service implements Storable {	
	public Restore() throws IOException {
		super();
		
		protocol = new GetChunk();
	}
}
