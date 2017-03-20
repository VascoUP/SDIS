package ui;

import java.util.Scanner;

public class UserInterface implements Runnable {

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
		if( inst.equals("BACKUP") ) {
			App.init_backup();
			App.end_backup();
		} else if( inst.equals("HELP") ) {
			System.out.println("INSTRUCTIONS:");
			System.out.println("Write \"BACKUP\" to backup a file");
			System.out.println("Write \"HELP\" for instruction");
		} else if( inst.equals("CLOSE") )
			end = true;
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