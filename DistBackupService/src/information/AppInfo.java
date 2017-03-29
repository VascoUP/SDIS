package information;

import java.util.ArrayList;
import java.util.Iterator;

import file.HandleFile;
import file.HandleXMLFile;
import ui.UserInterface;


public class AppInfo {
	private static ArrayList<Chunk> storedChunks;
	private static ArrayList<Chunk> backedUpChunks;
	
	private static Thread ui_thread;
	private static Storable ui;
	
	
	private static Thread backup_thread;
	private static Storable backup;
	
	private static Thread wait_backup_thread;
	private static Storable wait_backup;
	

	private static Thread restore_thread;
	private static Storable restore;
	
	private static Thread wait_restore_thread;
	private static Storable wait_restore;
	
	public static void init() {
		initStoredChunks();
		initBackedUpChunks();
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
	
	
	
	public static Thread getRestoreThread() {
		return restore_thread;
	}

	public static void setRestoreThread(Thread restore_thread) {
		AppInfo.restore_thread = restore_thread;
	}

	public static Storable getRestore() {
		return restore;
	}

	public static void setRestore(Storable restore) {
		AppInfo.restore = restore;
	}

	
	public static Thread getWaitRestoreThread() {
		return wait_restore_thread;
	}

	public static void setWaitRestoreThread(Thread wait_restore_thread) {
		AppInfo.wait_restore_thread = wait_restore_thread;
	}

	public static Storable getWaitRestore() {
		return wait_restore;
	}

	public static void setWaitRestore(Storable wait_restore) {
		AppInfo.wait_restore = wait_restore;
	}

	
	
	public static ArrayList<Chunk> getStoredChunks() {
		return storedChunks;
	}
	
	public static void initStoredChunks() {
		storedChunks = new ArrayList<Chunk>();
	}
	
	public static void addStoredChunk(Chunk chunk) {
		String path = chunk.getStorePath();
		if( HandleFile.isFile(path) )
			storeChunk(chunk);
		else
			fileElimStoredChunk(chunk);
	}
	
	public static void storeChunk(Chunk chunk) {
		storedChunks.add(chunk);
	}
	
	public static void removeStoredChunk(Chunk chunk) {
		storedChunks.remove(chunk);
		fileElimStoredChunk(chunk);
	}
	
	public static void fileElimStoredChunk(Chunk chunk) {
		try {
			HandleXMLFile.removeStoredChunk(chunk.getFileId(), "" + chunk.getChunkId());
		} catch (Exception e) {
			
		}
	}
	
	public static void fileAddStoredChunk(Chunk chunk) {
		try {
			HandleXMLFile.addStoreChunk(chunk);
		} catch (Exception e) {
			e.printStackTrace();
			return ;
		}
	}
	
	public static Chunk findStoredChunk(Chunk chunk) {
		Iterator<Chunk> iter = storedChunks.iterator();
		while(iter.hasNext()) {
			Chunk c = iter.next();
			if(iter.equals(chunk))
				return c;
		}
		return null;
	}
	
	
	
	public static ArrayList<Chunk> getBackedUpChunks() {
		return backedUpChunks;
	}
	
	public static void initBackedUpChunks() {
		backedUpChunks = new ArrayList<Chunk>();
	}
	
	public static void addBackedUpChunk(Chunk chunk) {
		eliminateBackedUpChunk(chunk);
	}

	public static void backupChunk(Chunk chunk) {
		backedUpChunks.add(chunk);
	}
	
	public static void removeBackedUpChunk(Chunk chunk) {
		backedUpChunks.remove(chunk);
		eliminateBackedUpChunk(chunk);
	}
	
	public static void eliminateBackedUpChunk(Chunk chunk) {
		try {
			HandleXMLFile.removeBackedUpChunk(chunk.getFileId(), "" + chunk.getChunkId(), chunk.getStorePath());
		} catch (Exception e) {
			
		}
	}
	
	public static void fileAddBackedUpChunk(Chunk chunk) {
		System.out.println("Backing up file to xml");
		try {
			HandleXMLFile.addBackedUpChunk(chunk);
		} catch (Exception e) {
			e.printStackTrace();
			return ;
		}
	}
	
	public static Chunk findBackedUpChunk(String path) {
		Iterator<Chunk> iter = backedUpChunks.iterator();
		while(iter.hasNext()) {
			Chunk c = iter.next();
			if(c.getStorePath().equals(path))
				return c;
		}
		return null;
	}
}
