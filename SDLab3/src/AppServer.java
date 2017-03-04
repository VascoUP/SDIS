import java.io.IOException;
import java.util.Vector;
import java.util.Iterator;
import Vehicles.RegisterVehicle;

/**
 * @author up201403485
 * @description Lab1
 * 
 * TCP client and server applications in Java
 * Use java.net library that has 3 useful classes DatagramSocket, DatagramPacket, Inet Address and SocketAccess
 * 
 * DatagramSocket 	-> Client - without arguments
 * 					-> Server - with port
 * 					|
 * 					|-> send( DatagramPacket packet )
 * 					|-> recv( DatagramPacket packet )
 * 					|-> setSoTimeout( int timeout )
 * 
 * DatagramPacket	- Constructor
 * 						|
 * 						|-> dest
 * 						|-> port
 * 						|-> data (byte[])
 * 
 * Inet Address		| Inet 4 address - Use this one
 * 							|-> getByName()
 * 					| Inet 6 address
 * 
 * SocketAccess
 * 
 * 			C			 S
 * 			|			 |
 * 			|----		 |
 * 			|    ----	 |
 * 			|	     --->|
 * 			|			 |
 * 			|	  	 ----|
 * 			|    ----	 |
 * 			|<---		 |
 * 			|			 |
 * 
 */

public class AppServer {
	
	// Server related
	private Connection info;
	
	// Program Related
	private Vector<RegisterVehicle> vehicles;

	public static final int regArgsSize = 4;
	public static final int lookArgsSize = 3;	
	
	
	
	public AppServer(int port) throws IOException {    	
    	this.vehicles = new Vector<RegisterVehicle>();
    	
    	this.info = new Connection(port);
    }    
	
	
	
    public boolean applyAction(String m) throws IOException {
    	String sendMessage = new String();	//string to be sent over to the client
    	boolean end = false;				//return boolean that indicates if the server should end
    	
    	String[] message = m.split(Constants.divideRegex);				//split message to make it easily usable
    	
    	
    	// the server should stop receiving messages
    	if( message[0].equals(Constants.endMessage) ) {    		
    		sendMessage = Constants.endMessage + Constants.divideRegex;
    		end = true;
    	}
    	// 1 - instruction (reg -> register vehicle
    	//					look -> lookup vehicle)
    	// Args -> 	| register 	-> owner's name
		//			|			-> plate number
    	//			| lookup	-> plate number
    	else if( message[0].equals(Constants.registerMessage) && message.length == regArgsSize ) {
    		    		
    		String name = message[1];
    		String plateNumber = message[2];
    		
    		RegisterVehicle vehicle = new RegisterVehicle(name, plateNumber);
    		if( register(vehicle) < 0 )
    			sendMessage = Constants.registerMessage + Constants.divideRegex + 
						Constants.errorMessage + Constants.divideRegex;
    		else
    			sendMessage = Constants.registerMessage + Constants.divideRegex + 
    					Constants.successMessage + Constants.divideRegex;
    		
    	} else if( message[0].equals(Constants.lookupMessage) && message.length == lookArgsSize ) {
    		String plateNumber = message[1];
    		String ownerName = lookup(plateNumber);
    		    		
    		sendMessage = Constants.lookupMessage + Constants.divideRegex + 
    				ownerName + Constants.divideRegex;
    		
    	} else
    		sendMessage = Constants.wrongArgumentsMessage + Constants.divideRegex;
    	
    	System.out.println(m + " : " + sendMessage);
    	info.sendMessage(sendMessage);
    	
    	return end;
    }
	
    public void receiveMessages() throws IOException {
    	while( true ) {
    		String rcv = info.receiveMessage();
    		if( rcv == null )
    			return ;
    		else if( applyAction(rcv) )
    			break;
    	}
    	info.closeConnection();
    }
    
    public void acceptClients() throws IOException {
    	while(true) {
    		info.waitClient();
    		receiveMessages();
    		if( info.getClosedConnection() )
    			break;
    	}
    }

    
    
    public int register(RegisterVehicle vehicle) {
    	if( this.vehicles.indexOf(vehicle) != -1 )
    		return -1;
    	this.vehicles.addElement(vehicle);
    	return this.vehicles.size();
    }
    
    public String lookup(String plate_no) {
    	Iterator<RegisterVehicle> it = vehicles.iterator();

    	while(it.hasNext()) {
    		RegisterVehicle vehicle = it.next();
    		
    		System.out.println(vehicle.getPlate_no());
    		
    		if( vehicle.getPlate_no().equals(plate_no) )
    			return vehicle.getName();
    	}
    	
    	return "NOT_FOUND";
    }
    
}
