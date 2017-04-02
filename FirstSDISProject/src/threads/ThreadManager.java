package threads;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import listener.*;
import message.backup.BackUpMessage;
import message.backup.StoredMessage;
import message.restore.ChunkMessage;
import message.restore.GetChunkMessage;
import sender.*;

public class ThreadManager {
	private static ArrayList<SenderThread> sender_threads = new ArrayList<SenderThread>();
	private static WorkerPool worker_pool;
	private static ListenerThread mc_listener;
	private static ListenerThread mdb_listener;
	private static ListenerThread mdr_listener;
	
	public static void initThreadManager() {
		initWorkerPool();
		initListenerThreads();
	}
	
	public static void initListenerThreads() {
		initMC();
		initMDB();
		initMDR();
	}
	
	public static void initMC() {
		MCListenner mc = null;
		try {
			mc = new MCListenner();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		Thread mc_thread = new Thread((Runnable)mc);
		mc_listener = new ListenerThread(mc_thread, mc);
		mc_listener.start();
	}
	
	public static void initMDB() {
		MDBListenner mdb = null;
		try {
			mdb = new MDBListenner();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		Thread mdb_thread = new Thread((Runnable)mdb);
		mdb_listener = new ListenerThread(mdb_thread, mdb);
		mdb_listener.start();		
	}
	
	public static void initMDR() {
		MDRListenner mdr = null;
		try {
			mdr = new MDRListenner();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		Thread mdr_thread = new Thread((Runnable)mdr);
		mdr_listener = new ListenerThread(mdr_thread, mdr);
		mdr_listener.start();
	}
	
	public static void initWorkerPool() {
		worker_pool = new WorkerPool();
		worker_pool.startAllWorkerThreads();
	}
	
	public static void initBackUp(BackUpMessage message) {
		update();
		
		if( findSenderThread("" + message) != null ) {
			System.out.println("Another backup like that one is already being processed");
			return ;
		}
		
		BackUpSender backup = null;
		try {
			backup = new BackUpSender(message);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		Thread backup_thread = new Thread((Runnable)backup);
		SenderThread st = new SenderThread(backup_thread, backup);
		sender_threads.add(st);
		st.start();
	}
	
	public static void initAnswerBackUp(StoredMessage message) {
		update();
		
		if( findSenderThread("" + message) != null ) {
			System.out.println("Another backup like that one is already being processed");
			return ;
		}
		
		AnswerBackUpSender aBackup = null;
		try {
			aBackup = new AnswerBackUpSender(message);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		Thread aBackup_thread = new Thread((Runnable)aBackup);
		SenderThread st = new SenderThread(aBackup_thread, aBackup);
		sender_threads.add(st);
		st.start();			
	}
	
	public static void initRestore(GetChunkMessage message) {
		update();

		if( findSenderThread("" + message) != null ) {
			System.out.println("Another backup like that one is already being processed");
			return ;
		}
		
		RestoreSender restore = null;
		try {
			restore = new RestoreSender(message);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		Thread restore_thread = new Thread((Runnable)restore);
		SenderThread st = new SenderThread(restore_thread, restore);
		sender_threads.add(st);
		st.start();			
	}
	
	public static void initAnswerRestore(ChunkMessage message) {
		update();

		if( findSenderThread("" + message) != null ) {
			System.out.println("Another backup like that one is already being processed");
			return ;
		}
		
		AnswerRestoreSender aRestore = null;
		try {
			aRestore = new AnswerRestoreSender(message);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		Thread aRestore_thread = new Thread((Runnable)aRestore);
		SenderThread st = new SenderThread(aRestore_thread, aRestore);
		sender_threads.add(st);
		st.start();				
	}

	public static void checkTerminatedSendThreads() throws InterruptedException {
		Iterator<SenderThread> iter = sender_threads.iterator();
		while( iter.hasNext() ) {
			SenderThread t = iter.next();
			System.out.println("checkTerminatedSendThreads: join dead thread");
			if( !t.isAlive() )
				t.close();
		}
	}

	public static void update() {
		try {
			checkTerminatedSendThreads();
		} catch (InterruptedException e1) {
		}
	}
	
	public static void joinSendThreads() throws InterruptedException {
		Iterator<SenderThread> iter = sender_threads.iterator();
		while( iter.hasNext() ) {
			SenderThread t = iter.next();
			System.out.println("joinSendThreads: join dead thread");
			t.close();
		}
	}
	
	public static void interruptThreads() throws InterruptedException {
		mc_listener.close();
		mdb_listener.close();
		mdr_listener.close();
	}
	
	public static void closeThreads() {
		worker_pool.shutdown();
		
		try { joinSendThreads();
		} catch (InterruptedException e) {
		}

		try { interruptThreads();
		} catch (InterruptedException e) {
		}
	}

	public static SenderThread findSenderThread(String name) {
		Iterator<SenderThread> iter = sender_threads.iterator();
		while( iter.hasNext() ) {
			SenderThread next = iter.next();
			if(next.getName().equals(name) )
				return next;
		}
		return null;
	}
}
