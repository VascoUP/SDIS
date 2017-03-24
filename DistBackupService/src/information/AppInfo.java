package information;

import java.util.ArrayList;
import java.util.Iterator;


public class AppInfo {
	private static ArrayList<String> storedChunks;
	
	private static Thread ui_thread;
	private static Storable ui;
	
	private static Thread backup_thread;
	private static Storable backup;
	
	private static Thread wait_backup_thread;
	private static Storable wait_backup;

	public static Thread getUIThread() {
		return ui_thread;
	}
	
	public static void setUIThread(Thread t) {
		ui_thread = t;
	}
	
	public static Storable getUI() {
		return ui;
	}
	
	public static void setUI( Storable s ) {
		ui = s;
	}
	
	public static Thread getBackupThread() {
		return backup_thread;
	}
	
	public static void setBackupThread(Thread t) {
		backup_thread = t;
	}
	
	public static Storable getBackup() {
		return backup;
	}
	
	public static void setBackup( Storable s ) {
		backup = s;
	}
	
	public static Thread getWaitBackupThread() {
		return wait_backup_thread;
	}
	
	public static void setWaitBackupThread(Thread t) {
		wait_backup_thread = t;
	}
	
	public static Storable getWaitBackup() {
		return wait_backup;
	}
	
	public static void setWaitBackp( Storable s ) {
		wait_backup = s;
	}
	
	
	
	public static ArrayList<String> getStoredChunks() {
		return storedChunks;
	}
	
	public static void initStoredChunks() {
		storedChunks = new ArrayList<String>();
	}
	
	public static void addChunk(String chunk) {
		storedChunks.add(chunk);
	}
	
	public static void removeChunk(String chunk) {
		storedChunks.remove(chunk);
	}
	
	public static String findChunk(String chunk) {
		Iterator<String> iter = storedChunks.iterator();
		while(iter.hasNext()) {
			String c = iter.next();
			if(iter.equals(chunk))
				return c;
		}
		return null;
	}
}
