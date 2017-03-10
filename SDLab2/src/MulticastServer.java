import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import Vehicles.RegisterVehicle;

import java.util.Iterator;

/**
 * @author up201403485
 * @description Lab1
 */
public class MulticastServer {
	
	// Server related
	private final int port;
	private final int mcast_port;
	
	private final DatagramSocket socket;
	private final MulticastSocket mcastSocket;
	
	private final InetAddress address;
	private final InetAddress mcastAddress;
	
	private final DatagramPacket mcastPacket;
	
	private final ScheduledFuture<?> periodicFuture;
	
	
	// Program Related
	private Vector<RegisterVehicle> vehicles;

	public static final int regArgsSize = 4;
	public static final int lookArgsSize = 3;	
	
	
	
	public MulticastServer(int src_port, String mcast_addr, int mcast_port) throws IOException {
		System.out.println(src_port + "\n" + mcast_addr + "\n" + mcast_port + "\n");
		
    	vehicles = new Vector<RegisterVehicle>();

    	this.mcast_port = mcast_port;
		port = src_port;
		
    	socket = new DatagramSocket(port);
    	address = InetAddress.getLocalHost();
		mcastSocket = new MulticastSocket(this.mcast_port);
		mcastAddress = InetAddress.getByName(mcast_addr);

		
		//Message to be sent over the multicast channel
    	String message = Constants.periodicMessage + Constants.divideRegex +
    						address.getHostName() + Constants.divideRegex +
    						port + Constants.divideRegex;
    	byte[] byteMessage = message.getBytes();
		mcastPacket = new DatagramPacket(byteMessage, byteMessage.length, 
											mcastAddress, this.mcast_port);
		
		
        // Create a scheduled thread pool with 5 core threads
        ScheduledThreadPoolExecutor sch = (ScheduledThreadPoolExecutor) 
                Executors.newScheduledThreadPool(1);
         
        // Create a task for one-shot execution using schedule()
        Runnable periodicMessage = new Runnable(){
            @Override
            public void run() {
            	try {
            		mcastSend();
            	} catch(IOException e) {
            		e.printStackTrace();
            	}
            	return ;
            }
        };

        periodicFuture = sch.scheduleAtFixedRate(periodicMessage, 0, 1, TimeUnit.SECONDS);
        
    }
	
	
	
    public boolean applyAction(DatagramPacket pckt) throws IOException {
    	String sendMessage = new String();	//string to be sent over to the client
    	boolean end = false;				//return boolean that indicates if the server should end
    	
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
    	
    	byte[] byteMessage = sendMessage.getBytes();
    	pckt.setData(byteMessage);
    	send(pckt);
    	
    	return end;
    }
	
    public void receiveMessages() throws IOException {
    	while( true ) {
    		DatagramPacket pckt = receive();
    		if( applyAction(pckt) )
    			break;
    	}
    	
        periodicFuture.cancel(false);
    	close();   	
    }
    
    
    
    public void send(DatagramPacket pckt) throws IOException {
		socket.send(pckt);    	
    }
    
    public void mcastSend() throws IOException {
    	mcastSocket.send(mcastPacket); 
    }
    
    public DatagramPacket receive() throws IOException {
    	DatagramPacket packet = new DatagramPacket(new byte[Constants.packetSize], Constants.packetSize);
		socket.receive(packet);
		return packet;
    }

    public void close() {
    	mcastSocket.close();
    }

    
    
    public int register(RegisterVehicle vehicle) {
    	if( vehicles.indexOf(vehicle) != -1 )
    		return -1;
    	vehicles.addElement(vehicle);
    	return vehicles.size();
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
