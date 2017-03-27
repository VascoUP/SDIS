package service.restore;

import java.io.IOException;

import information.Storable;
import protocol.restore.GetChunk;
import service.general.PontualService;

public class Restore extends PontualService implements Storable {	
	public Restore() throws IOException {
		super();
		
		protocol = new GetChunk();
	}
}
