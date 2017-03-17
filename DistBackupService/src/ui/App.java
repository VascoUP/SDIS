package ui;

import java.io.IOException;

import service.backup.BackUp;
import service.backup.WaitBackUp;

public class App {

	public static void main(String[] args) throws IOException {
		if(args.length != 1)
			return ;
		if( args[0].equals("BACKUP") )
			backup();
		else if( args[0].equals("SERVER") )
			wait_backup();			
	}
	
	private static void backup() throws IOException {
		BackUp b = new BackUp();
		b.backup_file();
	}
	
	private static void wait_backup() throws IOException {
		WaitBackUp w = new WaitBackUp();
		w.backup_file();
	}

}
