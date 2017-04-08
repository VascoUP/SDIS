package threads;

import java.io.IOException;

import listener.MCListener;
import listener.MDBListener;
import listener.MDRListener;
import sender.ChannelSender;

public class ThreadManager {
	private static WorkerPool worker_pool;
	private static BackUpPool backupServices;
	private static RestorePool restoreServices;
	private static ListenerThread mc_listener;
	private static ListenerThread mdb_listener;
	private static ListenerThread mdr_listener;
	
	public static void closeThreads() {
		worker_pool.shutdown();
		if( backupServices != null )
			backupServices.shutdown();
		if( restoreServices != null )
			restoreServices.shutdown();

		try { interruptThreads();
		} catch (InterruptedException e) {
		}
	}
	
	public static void initBackUp(ChannelSender sender) {
		if( backupServices == null )
			backupServices = new BackUpPool();
		backupServices.startBackupThread(sender);
	}
	
	public static void initListenerThreads() {
		initMC();
		initMDB();
		initMDR();
	}
	
	public static void initMC() {
		MCListener mc = null;
		try {
			mc = new MCListener();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		Thread mc_thread = new Thread(mc);
		mc_listener = new ListenerThread(mc_thread, mc);
		mc_listener.start();
	}
	
	public static void initMDB() {
		MDBListener mdb = null;
		try {
			mdb = new MDBListener();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		Thread mdb_thread = new Thread(mdb);
		mdb_listener = new ListenerThread(mdb_thread, mdb);
		mdb_listener.start();		
	}
	
	public static void initMDR() {
		MDRListener mdr = null;
		try {
			mdr = new MDRListener();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		Thread mdr_thread = new Thread(mdr);
		mdr_listener = new ListenerThread(mdr_thread, mdr);
		mdr_listener.start();
	}

	public static void initRestore(ChannelSender sender) {
		if( restoreServices == null )
			restoreServices = new RestorePool();
		restoreServices.startRestoreThread(sender);
	}

	public static void initThreadManager() {
		initWorkerPool();
		initListenerThreads();
	}
	
	public static void initWorkerPool() {
		worker_pool = new WorkerPool();
		worker_pool.startAllWorkerThreads();
	}
	
	public static void interruptThreads() throws InterruptedException {
		mc_listener.close();
		mdb_listener.close();
		mdr_listener.close();
	}
}
