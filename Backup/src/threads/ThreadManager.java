package threads;

import java.io.IOException;

import listener.MCListener;
import listener.MDBListener;
import listener.MDRListener;
import sender.ChannelSender;

/**
 *
 * This class builds a thread manager
 *
 */
public class ThreadManager {
	private static WorkerPool worker_pool;			//Worker pool
	private static BackUpPool backupServices;		//Backup pool
	private static RestorePool restoreServices;		//Restore pool
	private static ListenerThread mc_listener;		//MC listener
	private static ListenerThread mdb_listener;		//MDB listener
	private static ListenerThread mdr_listener;		//MDR listener
	
	/**
	 * Closes the backup and restore threads
	 */
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
	
	/**
	 * Initiates the backup threads
	 * @param sender Sender channels
	 */
	public static void initBackUp(ChannelSender sender) {
		if( backupServices == null )
			backupServices = new BackUpPool();
		backupServices.startBackupThread(sender);
	}
	
	/**
	 * Initiates the listener threads
	 */
	public static void initListenerThreads() {
		initMC();
		initMDB();
		initMDR();
	}
	
	/**
	 * Initiates the MC listener
	 */
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
	
	/**
	 * Initiates the MDB listener
	 */
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
	
	/**
	 * Initiates the MDR listener
	 */
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

	/**
	 * Initiates the restore thread
	 * @param sender Sender channel
	 */
	public static void initRestore(ChannelSender sender) {
		if( restoreServices == null )
			restoreServices = new RestorePool();
		restoreServices.startRestoreThread(sender);
	}

	/**
	 * Initiates the thread's manager
	 */
	public static void initThreadManager() {
		initWorkerPool(); 		//Initiates the worker pool
		initListenerThreads();	//Initiates the listener threads
	}
	
	/**
	 * Initiates the worker pool/thread
	 */
	public static void initWorkerPool() {
		worker_pool = new WorkerPool();
		worker_pool.startAllWorkerThreads();
	}
	
	/**
	 * Interrupts the listener threads
	 * @throws InterruptedException Thrown when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity
	 */
	public static void interruptThreads() throws InterruptedException {
		mc_listener.close();
		mdb_listener.close();
		mdr_listener.close();
	}
}
