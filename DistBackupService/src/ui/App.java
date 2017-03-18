package ui;

import java.io.IOException;
import java.util.ArrayList;

import service.backup.BackUp;
import service.backup.WaitBackUp;

public class App {
	private static ArrayList<Thread> important_threads = new ArrayList<Thread>();
	private static ArrayList<Thread> listener_threads = new ArrayList<Thread>();

	public static void main(String[] args) {
		if(args.length != 1)
			return ;
		
		//listeners();
		
		if( args[0].equals("BACKUP") )
			backup();
		else if( args[0].equals("SERVER") )
			important_threads.add(wait_backup());
		
		wait_threads();
		close_threads();
	}
	
	private static void wait_threads() {
		System.out.println("Waiting for imp threads...");
		for( int i = 0; i < important_threads.size(); i++ ) {
			try {
				important_threads.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Closed imp threads");
	}
	
	private static void close_threads() {
		for( int i = 0; i < important_threads.size(); i++ )
			important_threads.get(i).interrupt();
		
		System.out.println("Closed all other threads");	
	}
	
	private static void listeners() {
		listener_threads.add(wait_backup());
	}
	
	private static void backup() {
		BackUp b = null;
		try {
			b = new BackUp("yomama.pdf");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		Thread t = new Thread(b);
		t.start();
		important_threads.add(t);
	}
	
	private static Thread wait_backup() {
		WaitBackUp w = null;
		try {
			w = new WaitBackUp();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		
		Thread t = new Thread(w);
		t.start();
		return t;
	}

}
