package ui;

import java.util.Scanner;

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
		if( inst.equals("BACKUP") )
			action_backup();
		else if( inst.equals("HELP") )
			action_help();
		else if( inst.equals("CLOSE") )
			end = true;
	}
	
	public void action_backup() {
		App.init_backup();
		App.end_backup();
	}
	
	public void action_help() {
		System.out.println("INSTRUCTIONS:");
		System.out.println("Write \"CLOSE\" to close the program");
		System.out.println("Write \"BACKUP\" to backup a file");
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