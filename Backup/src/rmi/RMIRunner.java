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
	public static String parseArgs(String[] rmiArgs) {
		if( rmiArgs.length < 1 )
			return "Wrong arguments";
		
		String protocol = rmiArgs[0];
		String path;
		int rep_degree;
		
		//Different protocols
		switch(protocol) {
		case "BACKUP":
			if( rmiArgs.length != 3 )
				return "Wrong arguments for protocol BACKUP";
			path = rmiArgs[1];
			rep_degree = Integer.parseInt(rmiArgs[2]);
			return backUp(path, rep_degree);
		case "RESTORE":
			if( rmiArgs.length != 2 )
				return "Wrong arguments for protocol RESTORE";
			path = rmiArgs[1];
			return restore(path);
		case "DELETE":
			if( rmiArgs.length != 2 )
				return "Wrong arguments for protocol DELETE";
			path = rmiArgs[1];
			return delete(path);
		case "CLOSE":
			if( rmiArgs.length != 1 )
				return "Wrong arguments for protocol CLOSE";
			return close();
		}
		
		return "Undefined protocol";
	}

	/**
	 * Initiates the backup's service
	 * @param path File's pathname
	 * @param rep_degree Replication's degree
	 */
	public static String backUp(String path, int rep_degree) {
		System.out.println("Backup");
		Protocol backup = new BackUp(path, rep_degree);
		backup.run_service();
		return "Successful backup";
	}
	
	/**
	 * Closes the threads and finishes the services
	 */
	public static String close() {
		System.out.println("Close");	
		ThreadManager.closeThreads();
		System.exit(0);
		//RMIStorage.getRMI().unbind();
		return "Successful close";
	}
	
	/**
	 * Initiates the delete's protocol
	 * @param path File's pathname
	 */
	public static String delete(String path) {
		System.out.println("Delete");
		Protocol delete;
		try {
			delete = new Delete(path);
			delete.run_service();
		} catch (Exception e) {
			return "Unsuccessful delete";
		}
		return "Successful delete";
	}
	
	/**
	 * Initiates the restore's protocol
	 * @param path File's pathname
	 */
	public static String restore(String path) {
		System.out.println("Restore");		
		try {
			Protocol restore = new Restore(path);
			restore.run_service();
		} catch (Exception e) {
			return "Unsuccessful restore";
		}
		return "Successful restore";
	}
}