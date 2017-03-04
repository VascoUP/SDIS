import java.io.IOException;

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
    	if( args.length != Constants.clientArgsSize ) {
    		System.out.println(Constants.clientUsage);
    		return ;
    	}
    	
		AppClient client;
		int port = Integer.parseInt(args[2]);
		
		try {
			client = new AppClient(args[1], port, args[3]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ;
		}    
		
		try {
			client.sendMessages();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ;
		}	
    }
    
    public static void serverOp(String[] args) {
		//Main.state = State.Server;
    	if( args.length != Constants.serverArgsSize ) {
    		System.out.println(Constants.serverUsage);
    		return ;
    	}
    	
		AppServer server;
		int port = Integer.parseInt(args[1]);
		
		try {
			server = new AppServer(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ;
		}
		
		try {
			server.acceptClients();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ;
		}
    }
}
