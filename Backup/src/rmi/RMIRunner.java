package rmi;

import protocol.BackUp;
import protocol.Delete;
import protocol.Protocol;
import protocol.Restore;
import threads.ThreadManager;

public class RMIRunner {
	
	public static void parseArgs(String[] rmiArgs) {
		if( rmiArgs.length < 1 )
			return ;
		
		String protocol = rmiArgs[0];
		String path;
		int rep_degree;
		
		switch(protocol) {
		case "BACKUP":
			if( rmiArgs.length != 3 )
				return ;
			path = rmiArgs[1];
			rep_degree = Integer.parseInt(rmiArgs[2]);
			backUp(path, rep_degree);
			break;
		case "RESTORE":
			if( rmiArgs.length != 2 )
				return ;
			path = rmiArgs[1];
			restore(path);
			break;
		case "DELETE":
			if( rmiArgs.length != 2 )
				return ;
				path = rmiArgs[1];
			
		case "CLOSE":
			if( rmiArgs.length != 1 )
				return ;
			close();
		}
		return ;
	}

	public static void backUp(String path, int rep_degree) {
		System.out.println("Backup");
		Protocol backup = new BackUp(path, rep_degree);
		backup.run_service();
	}
	
	public static void close() {
		System.out.println("Close");	
		ThreadManager.closeThreads();
		System.exit(0);
		//RMIStorage.getRMI().unbind();
	}
	
	public static void delete(String path) {
		System.out.println("Delete");
		Protocol delete = new Delete(path);
		delete.run_service();
	}
	
	public static void restore(String path) {
		System.out.println("Restore");		
		try {
			Protocol restore = new Restore(path);
			restore.run_service();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}