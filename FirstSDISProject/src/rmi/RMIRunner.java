package rmi;

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
		}
		return ;
	}
	
	
	public static void backUp(String path, int rep_degree) {
		System.out.println("Backup");
		ThreadManager.initBackUpThread(path);
		ThreadManager.joinBackUpThread();
	}
	
	public static void restore(String path) {
		System.out.println("Restore");
		ThreadManager.initRestoreThread(path);
		ThreadManager.joinRestoreThread();
	}
}
