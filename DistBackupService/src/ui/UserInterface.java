package ui;

import java.util.ArrayList;
import java.util.Scanner;

import information.AppInfo;
import information.Chunk;
import information.Storable;

public class UserInterface implements Runnable, Storable {

	private Scanner scan;
	private String instruction;
	private boolean end;
	
	
	public UserInterface() {
		scan = new Scanner (System.in);
		instruction = "";
		end = false;
	}
	
	
	public String getInstruction() {
		return instruction;
	}
	
	public void setEnd() {
		end = !end;
	}
	
	
	public void action(String inst) {
		String[] instTrim = inst.split("");
		if( instTrim.length == 2 && instTrim[0].equals("BACKUP") )
			action_backup(instTrim[1]);
		else if( instTrim.length == 2 && instTrim[0].equals("RESTORE") )
			action_restore(instTrim[1]);
		else if( instTrim.length == 1 && instTrim[0].equals("STOREDCHUNKS") )
			action_stored_chunks();
		else if( instTrim.length == 1 && instTrim[0].equals("BACKEDUPCHUNKS") )
			action_bu_chunks();
		else if( instTrim.length == 1 && instTrim[0].equals("HELP") )
			action_help();
		else if( instTrim.length == 1 && instTrim[0].equals("CLOSE")  )
			end = true;
	}
	
	public void action_backup(String inst) {
		App.init_backup(inst);
		App.end_backup();
	}
	
	public void action_restore(String inst) {
		App.init_restore(inst);
		App.end_restore();		
	}
	
	public void action_stored_chunks() {
		ArrayList<Chunk> chunks = AppInfo.getStoredChunks();
		System.out.println("Found " + chunks.size() + " chunks");
		for( Chunk c : chunks )
			System.out.println("Chunk " + c.getChunkId() + " from " + c.getFileId() + "\n" + 
								"Store in: " + c.getStorePath());
	}
	
	public void action_bu_chunks() {
		ArrayList<Chunk> chunks = AppInfo.getBackedUpChunks();
		System.out.println("Found " + chunks.size() + " chunks");
		for( Chunk c : chunks )
			System.out.println("Chunk " + c.getChunkId() + " from " + c.getFileId() + "\n" + 
								"Store in: " + c.getStorePath());
	}
	
	public void action_help() {
		System.out.println("INSTRUCTIONS:");
		System.out.println("Write \"CLOSE\" to close the program");
		System.out.println("Write \"BACKUP <file_path>\" to backup a file");
		System.out.println("Write \"RESTORE <file_path>\" to restore a file");
		System.out.println("Write \"STOREDCHUNKS\" to check localy stored chunks");
		System.out.println("Write \"BACKEDUPCHUNKS\" to check backed up chunks");
		System.out.println("Write \"HELP\" for instruction");
	}
	
	
	@Override
	public void run() {
		System.out.println("Write \"HELP\" for instruction");
		
		while( !end ) {
			System.out.print("\n: ");  
			instruction = scan.nextLine();
			action(instruction);
		}
	}

}