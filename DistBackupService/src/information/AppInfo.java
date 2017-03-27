package information;

import java.util.ArrayList;
import java.util.Iterator;

import file.HandleXMLFile;
import ui.UserInterface;


public class AppInfo {
	private static ArrayList<Chunk> storedChunks;
	
	private static Thread ui_thread;
	private static Storable ui;
	
	private static Thread backup_thread;
	private static Storable backup;
	
	private static Thread wait_backup_thread;
	private static Storable wait_backup;
	
	public static void init() {
		initStoredChunks();
		try {
			HandleXMLFile.readDocument();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		initUI();
	}

	public static Thread getUIThread() {
		return ui_thread;
	}
	
	public static void setUIThread(Thread t) {
		ui_thread = t;
	}
	
	public static void initUI() {
		ui = new UserInterface();
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
	
	
	
	public static ArrayList<Chunk> getStoredChunks() {
		return storedChunks;
	}
	
	public static void initStoredChunks() {
		storedChunks = new ArrayList<Chunk>();
	}
	
	public static void addChunk(Chunk chunk) {
		storedChunks.add(chunk);
	}
	
	public static void removeChunk(Chunk chunk) {
		storedChunks.remove(chunk);
	}
	
	public static Chunk findChunk(Chunk chunk) {
		Iterator<Chunk> iter = storedChunks.iterator();
		while(iter.hasNext()) {
			Chunk c = iter.next();
			if(iter.equals(chunk))
				return c;
		}
		return null;
	}
}
