package ui;

import java.io.IOException;

import information.AppInfo;
import service.backup.BackUp;
import service.backup.WaitBackUp;

public class App {	
	private static int serverId;
	private static String versionProtocol;

	public static void main(String[] args) {
		if(args.length != 2)
			return ;

		versionProtocol = args[0];
		serverId = Integer.parseInt(args[1]);

		prog();
	}
	
	private static void prog() {
		init();

		try {
			AppInfo.getUIThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			end();
			return ;
		}
		
		end();
	}



	public static void init() {
		AppInfo.setUI(new UserInterface());
		
		init_wait_backup();
		init_UI();
	}
	
	private static void end() {
		wait_threads();
		close_threads();
	}	
	
	
	public static void wait_threads() {
		end_backup();
	}
	
	public static void close_threads() {
		end_wait_backup();
	}
	
	
	public static void init_UI() {
		AppInfo.setUIThread(new Thread((Runnable)AppInfo.getUI()));
		AppInfo.getUIThread().start();
	}
	
	
	public static void init_backup() {
		try {
			AppInfo.setBackup(new BackUp("Yomama.pdf"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		AppInfo.setBackupThread(new Thread((Runnable)AppInfo.getBackup()));
		AppInfo.getBackupThread().start();
	}
	
	public static void end_backup() {
		if(AppInfo.getBackupThread() == null )
			return ;
		
		try {
			AppInfo.getBackupThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	public static void init_wait_backup() {
		try {
			AppInfo.setWaitBackp(new WaitBackUp());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		
		AppInfo.setWaitBackupThread(new Thread((Runnable)AppInfo.getWaitBackup()));
		AppInfo.getWaitBackupThread().start();
	}

	public static void end_wait_backup() {
		AppInfo.getWaitBackupThread().interrupt();

		WaitBackUp wbp = (WaitBackUp)AppInfo.getWaitBackup();
		wbp.end_service();
	}

	
	public static int getServerId() {
		return serverId;
	}

	public static String getVersionProtocol() {
		return versionProtocol;
	}

}
