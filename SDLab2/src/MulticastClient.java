import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * @author up201403485
 * @description Lab1
 */
public class MulticastClient {
	
	// Socket related
	private int port;
	private final int mcast_port;
	
	private DatagramSocket socket;
	private final MulticastSocket mcast_socket;
	
	private InetAddress address;
	private final InetAddress mcast_address;
	
	private DatagramPacket packet;
	
	private static final int timeOut = 2000;
	public static final int periodicArgsSize = 4;
	
	
	// Program Related	
	private String message;
	//private String op;
	//private String[] messages;
	
	
	
    public MulticastClient(String mcast_addr, int mcast_port, String m) throws IOException {
    	port = -1;
    	this.mcast_port = mcast_port;
    	
    	// create socket
		socket = new DatagramSocket();
		socket.setSoTimeout(timeOut);
		mcast_socket = new MulticastSocket(this.mcast_port);
		mcast_address = InetAddress.getByName(mcast_addr);
		mcast_socket.joinGroup(mcast_address);
		mcast_socket.setSoTimeout(timeOut);
		
    	// get message from args
    	message = m;
    }
	
    
    
    public void sendMessages() throws IOException {
    	String info = "";
    	String[] parseInfo = new String[0];
    	
    	do {
        	// receive info to connect directly to the server
    		info = mcastReceive();
    		parseInfo = info.split(Constants.divideRegex);
    	} while( parseInfo.length != periodicArgsSize || !parseInfo[0].equals(Constants.periodicMessage) );
    	
    	address = InetAddress.getByName(parseInfo[1]);
    	port = Integer.parseInt(parseInfo[2]);

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
    	packet = new DatagramPacket(byteMessage, byteMessage.length, address, port);
		socket.send(this.packet);
    }
    
    public String receive() throws SocketTimeoutException, IOException {
    	DatagramPacket packet = new DatagramPacket(new byte[Constants.packetSize], Constants.packetSize);
		socket.receive(packet);
		return new String(packet.getData(), StandardCharsets.UTF_8);
    }    
    
    public String mcastReceive() throws SocketTimeoutException, IOException {
    	DatagramPacket packet = new DatagramPacket(new byte[Constants.packetSize], Constants.packetSize);
    	mcast_socket.receive(packet);
		return new String(packet.getData(), StandardCharsets.UTF_8);
    }
    
    public void close() throws IOException {
    	mcast_socket.leaveGroup(mcast_address);
    	mcast_socket.close();
    	socket.close();
    }
    
}
