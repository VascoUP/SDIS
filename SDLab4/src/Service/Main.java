package Service;
import java.util.Arrays;

public class Main {
	
	public static void main(String[] args) {
    	if( args.length < 1 ) {
    		System.out.println(Constants.serverUsage);
    		System.out.println(Constants.clientUsage);
	    	return;
    	} 
    	
    	if( args[0].equals(Constants.clientArg) )
    		clientOp(args);     		
    	else if( args[0].equals(Constants.serverArg) )
    		serverOp(args);
    }
    
	
    public static void clientOp(String[] args) {
		//Main.state = State.Client;
    	if( args.length < Constants.clientMinArgsSize ) {
    		System.out.println(Constants.clientUsage);
    		System.out.println("Size: " + args.length + "; Desired: " + Constants.clientMinArgsSize);
    		return ;
    	}
    	
		AppClient client;
		String[] operations = Arrays.copyOfRange(args, 4, args.length);
		
		client = new AppClient(args[1], args[2], args[3], operations);
		client.connect();
    }
    
    public static void serverOp(String[] args) {
		//Main.state = State.Server;
    	if( args.length != Constants.serverArgsSize ) {
    		System.out.println(Constants.serverUsage);
    		return ;
    	}
    	
		String rom = args[1];
		
		AppServer server = new AppServer(rom);
		server.connect();
    }
}
