package threads;

import listener.MCListenner;
import listener.MDBListenner;
import listener.MDRListenner;
import message.MessageInfoGetChunk;
import sender.ChannelSender;

import java.io.IOException;

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

		interruptThreads();
	}
	
	public static void initBackUp(ChannelSender sender) {
		if( backupServices == null )
			backupServices = new BackUpPool();
		backupServices.startBackupThread(sender);
	}
	
	private static void initListenerThreads() {
		initMC();
		initMDB();
		initMDR();
	}
	
	private static void initMC() {
		MCListenner mc = null;
		try {
			mc = new MCListenner();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		Thread mc_thread = new Thread(mc);
		mc_listener = new ListenerThread(mc_thread, mc);
		mc_listener.start();
	}
	
	private static void initMDB() {
		MDBListenner mdb = null;
		try {
			mdb = new MDBListenner();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		Thread mdb_thread = new Thread(mdb);
		mdb_listener = new ListenerThread(mdb_thread, mdb);
		mdb_listener.start();		
	}
	
	private static void initMDR() {
		MDRListenner mdr = null;
		try {
			mdr = new MDRListenner();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		Thread mdr_thread = new Thread(mdr);
		mdr_listener = new ListenerThread(mdr_thread, mdr);
		mdr_listener.start();
	}

	public static void initRestore(MessageInfoGetChunk message) {
		if( restoreServices == null )
			restoreServices = new RestorePool();
		restoreServices.startRestoreThread(message);
	}

	public static void initThreadManager() {
		initWorkerPool();
		initListenerThreads();
	}
	
	private static void initWorkerPool() {
		worker_pool = new WorkerPool();
		worker_pool.startAllWorkerThreads();
	}
	
	private static void interruptThreads() {
		mc_listener.close();
		mdb_listener.close();
		mdr_listener.close();
	}
}
