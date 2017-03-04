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
    	
    	MulticastClient client;
    	String mcast_addr = args[1];
    	String message = args[3];
    	int mcast_port;
    	
    	try {
    		mcast_port = Integer.parseInt(args[2]);
    	} catch(NumberFormatException e) {
    		e.printStackTrace();
    		return ;
    	}
    	
		
		try {
			client = new MulticastClient(mcast_addr, mcast_port, message);
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
    	
    	MulticastServer server;
    	int src_port, mcast_port;
		String mcast_addr = args[2];
    	
    	try {
    		src_port = Integer.parseInt(args[1]);
			mcast_port = Integer.parseInt(args[3]);
    	} catch(NumberFormatException e) {
    		e.printStackTrace();
    		return ;
    	}
    	
		
		try {
			server = new MulticastServer(src_port, mcast_addr, mcast_port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ;
		}
		
		try {
			server.receiveMessages();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ;
		}
    }
}
