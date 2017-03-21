package ui;

import java.io.IOException;

import service.backup.BackUp;
import service.backup.WaitBackUp;

public class App {	
	private static Thread ui_thread;
	private static UserInterface ui;
	
	private static Thread backup_thread;
	private static BackUp backup;
	
	private static Thread wait_backup_thread;
	private static WaitBackUp wait_backup;

	public static void main(String[] args) {
		if(args.length != 0)
			return ;

		prog();
	}
	
	private static void prog() {
		init();

		try {
			ui_thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			end();
			return ;
		}
		
		end();
	}



	public static void init() {
		ui = new UserInterface();
		
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
		ui_thread = new Thread(ui);
		ui_thread.start();
	}
	
	
	public static void init_backup() {
		try {
			backup = new BackUp("yomama.pdf");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		backup_thread = new Thread(backup);
		backup_thread.start();
	}
	
	public static void end_backup() {
		if(backup_thread == null )
			return ;
		
		try {
			backup_thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	public static void init_wait_backup() {
		wait_backup = null;
		try {
			wait_backup = new WaitBackUp();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		
		wait_backup_thread = new Thread(wait_backup);
		wait_backup_thread.start();
	}

	public static void end_wait_backup() {
		wait_backup_thread.interrupt();
		
		try {
			wait_backup.end();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
