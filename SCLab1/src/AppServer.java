import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Vector;
import java.util.Iterator;
import Vehicles.RegisterVehicle;

/**
 * @author up201403485
 * @description Lab1
 * 
 * UDP client and server applications in Java
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
	private DatagramSocket sckt;
	
	// Program Related
	private Vector<RegisterVehicle> vehicles;

	public static final int regArgsSize = 4;
	public static final int lookArgsSize = 3;	
	
	
	
	public AppServer() throws IOException {    	
    	this.vehicles = new Vector<RegisterVehicle>();
    	
		this.sckt = new DatagramSocket(Constants.port);
    }    
	
	
	
    public boolean applyAction(DatagramPacket pckt) throws IOException {
    	String sendMessage = new String();	//string to be sent over to the client
    	boolean end = false;				//return boolean that indicates if the server should end
    	
    	InetAddress add = pckt.getAddress();							//address of the client that sent the packet
    	int port = pckt.getPort();										//port of the client that sent the packet
    	String m = new String(pckt.getData(), StandardCharsets.UTF_8);	//message sent by the client
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
    	
    	send(port, add, sendMessage);
    	
    	return end;
    }
	
    public void receiveMessages() throws IOException {
    	while( true ) {
    		DatagramPacket pckt = receive();
    		if( applyAction(pckt) )
    			break;
    	}
    	close();   	
    }
    
    
    
    public void send(int port, InetAddress add, String message) throws IOException {
    	byte[] byteMessage = message.getBytes();
    	DatagramPacket packet = new DatagramPacket(byteMessage, byteMessage.length, add, port);
		this.sckt.send(packet);    	
    }
    
    public DatagramPacket receive() throws IOException {
    	DatagramPacket packet = new DatagramPacket(new byte[Constants.packetSize], Constants.packetSize);
		this.sckt.receive(packet);
		return packet;
    }

    public void close() {
    	this.sckt.close();
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
