package rmi;

import protocol.BackUp;
import protocol.Delete;
import protocol.Protocol;
import protocol.Restore;
import threads.ThreadManager;

/**
 * 
 * This class builds a RMI's runner
 *
 */
public class RMIRunner {
	
	/**
	 * Parses the different rmi's arguments
	 * @param rmiArgs Arguments that will be parsed
	 */
	public static void parseArgs(String[] rmiArgs) {
		if( rmiArgs.length < 1 )
			return ;
		
		String protocol = rmiArgs[0];
		String path;
		int rep_degree;
		
		//Different protocols
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
			delete(path);
			break;
		case "CLOSE":
			if( rmiArgs.length != 1 )
				return ;
			close();
		}
		return ;
	}

	/**
	 * Initiates the backup's service
	 * @param path File's pathname
	 * @param rep_degree Replication's degree
	 */
	public static void backUp(String path, int rep_degree) {
		System.out.println("Backup");
		Protocol backup = new BackUp(path, rep_degree);
		backup.run_service();
	}
	
	/**
	 * Closes the threads and finishes the services
	 */
	public static void close() {
		System.out.println("Close");	
		ThreadManager.closeThreads();
		System.exit(0);
		//RMIStorage.getRMI().unbind();
	}
	
	/**
	 * Initiates the delete's protocol
	 * @param path File's pathname
	 */
	public static void delete(String path) {
		System.out.println("Delete");
		Protocol delete;
		try {
			delete = new Delete(path);
			delete.run_service();
		} catch (Exception ignore) {
		}
	}
	
	/**
	 * Initiates the restore's protocol
	 * @param path File's pathname
	 */
	public static void restore(String path) {
		System.out.println("Restore");		
		try {
			Protocol restore = new Restore(path);
			restore.run_service();
		} catch (Exception ignore) {
		}
	}
}