import java.io.IOException;

/**
 * @author up201403485
 * @description Lab1
 */

public class AppClient {
	
	// Socket related
	private Connection info;
		
	
	// Program Related	
	private String message;
	
    
    public AppClient(String hostName, int port, String m) throws IOException {
    	this.info = new Connection(hostName, port);
    	
    	// Don't forget timeout
		
    	// get message from args
    	message = m;
    }
	
    
    
    public void sendMessages() throws IOException {
    	sendMessage(this.message);
    	//sendMessage(Constants.endMessage + ":");
    	info.closeConnection();
    }
    
    public boolean validAnswer(String[] rcvM, String[] sntM) {
    	return rcvM.length > 0 && 
    				(rcvM[0].equals(sntM[0]) || 
    				rcvM[0].equals(Constants.wrongArgumentsMessage));
    }
    
    public void sendMessage(String m) throws IOException {    	
    	String[] sntM = m.split(Constants.divideRegex);
    	String[] rcvM = new String[0];
    	
    	do {	    	
	    	info.sendMessage(m);
	    	String rcv = new String();
    		rcv = info.receiveMessage();
    		rcvM = rcv.split(Constants.divideRegex);
    	} while(!validAnswer(rcvM, sntM));
    	
    	if( rcvM.length > 2 )
    		System.out.println(rcvM[1]);
    	else
    		System.out.println("Receive: " + rcvM[0]);
    		
    }    
}
