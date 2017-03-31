package threads;

import java.io.IOException;

import information.AppInfo;
import service.backup.BackUp;
import service.backup.WaitBackUp;
import service.restore.Restore;
import service.restore.WaitRestore;

public class ThreadManager {
	
	/*==============
	 * INIT THREADS
	 *==============
	 */
	public static void initListenerThreads() {
		initWaitBackup();
		initWaitRestore();
	}
	
	public static void initBackUpThread(String path) {
		try {
			AppInfo.setBackup(new BackUp(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		AppInfo.setBackupThread(new Thread((Runnable)AppInfo.getBackup()));
		AppInfo.getBackupThread().start();
	}
	
	public static void initRestoreThread(String path) {
		try {
			AppInfo.setRestore(new Restore(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		AppInfo.setRestoreThread(new Thread((Runnable)AppInfo.getRestore()));
		AppInfo.getRestoreThread().start();
	}

	public static void initWaitBackup() {
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

	public static void initWaitRestore() {
		try {
			AppInfo.setWaitRestore(new WaitRestore());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		AppInfo.setWaitRestoreThread(new Thread((Runnable) AppInfo.getWaitRestore()));
		AppInfo.getWaitRestoreThread().start();		
	}


	/*===============
	 * CLOSE THREADS
	 *===============
	 */
	public static void closeThreads() {
		joinServiceThreads();
		interruptWaitBackUp();
	}
	
	public static void interruptWaitBackUp() {
		AppInfo.getWaitBackupThread().interrupt();
		AppInfo.getWaitRestoreThread().interrupt();
	}
	
	public static void joinServiceThreads() {
		joinBackUpThread();
		joinRestoreThread();
	}

	public static void joinBackUpThread() {
		try {
			AppInfo.getBackupThread().join();
		} catch (InterruptedException e) {
		}
	}
	
	public static void joinRestoreThread() {
		try {
			AppInfo.getRestoreThread().join();
		} catch (InterruptedException e) {
		}
	}
}
