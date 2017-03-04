import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * @author up201403485
 * @description Lab1
 */

public class AppClient {
	
	// Socket related
	private DatagramSocket sckt;
	private DatagramPacket pckt;
	private InetAddress address;
	
	private static final int timeOut = 1000;
	
	
	// Program Related	
	private String message;
	
    
    public AppClient(String name, String m) throws IOException {		
    	// create socket
		this.sckt = new DatagramSocket();
		this.sckt.setSoTimeout(timeOut);
		
    	// create InetAddress
		this.address = InetAddress.getByName(name);
		
    	// get message from args
    	message = m;
    }
	
    
    
    public void sendMessages() throws IOException {
    	// send message
    	sendMessage(this.message);
    	
		close();
    }
    
    
    
    public void sendMessage(String m) throws IOException {
    	int nRepeats = 0;
    	boolean repeat;
    	
    	String[] dM;
    	String[] dRM = new String[0];
    	
    	do {
    		repeat = false;
	    	dM = m.split(Constants.divideRegex);
	    	
	    	send(m);
	    	String receiveMessage = new String();
	    	
	    	try {
	    		receiveMessage = receive();
	    	} catch(SocketTimeoutException e) {
	    		System.out.println("Can't connect");
	    		repeat = true;
	    		nRepeats++;
	    	}
	    	
	    	if( !repeat )
	    		dRM = receiveMessage.split(Constants.divideRegex);
	    	else if( nRepeats >= Constants.maxRepeats ) {
	    		System.out.println("Couldn't connect to the server\nClosing now...");
	    		return;
	    	}
	    	
    	} while( repeat || dRM.length < 1 || dM.length < 1 || 
    			( !dRM[0].equals(dM[0]) && !dRM[0].equals(Constants.wrongArgumentsMessage) ) );
    	
    	if( dRM.length > 2 )
    		System.out.println(dRM[1]);
    	else
    		System.out.println("Receive: " + dRM[0]);
    		
    }
    
    public void send(String m) throws IOException {
    	byte[] byteMessage = m.getBytes();
    	this.pckt = new DatagramPacket(byteMessage, byteMessage.length, this.address, Constants.port);
		this.sckt.send(this.pckt);
    }
    
    public String receive() throws SocketTimeoutException, IOException {
    	DatagramPacket packet = new DatagramPacket(new byte[Constants.packetSize], Constants.packetSize);
		this.sckt.receive(packet);
		return new String(packet.getData(), StandardCharsets.UTF_8);
    }
    
    public void close() {
    	this.sckt.close();
    }
    
}
