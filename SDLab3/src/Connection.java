import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection {
	private ServerSocket srvSocket;
	private Socket socket;
	private InetAddress address;
	
	private PrintWriter out;
	private BufferedReader in;
	
	private boolean closedConnection = false;
	
	// Server constructor
	public Connection(int port) throws IOException {
		this.srvSocket = new ServerSocket(port);
	}
	
	// Client constructor
	public Connection(String hostName, int port) throws IOException {
		this.address = InetAddress.getByName(hostName);
		this.socket = new Socket(hostName, port);

		this.initIO();
	}
	
	public void waitClient() throws IOException {
		this.socket = this.srvSocket.accept();
		System.out.println("Accepted");
		
		this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		this.out = new PrintWriter(this.socket.getOutputStream(), true);
		
		this.initIO();
	}
	
	public void initIO() throws IOException {
		this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		this.out = new PrintWriter(this.socket.getOutputStream(), true);		
	}
	
	
	/* 
	 * --------------------
	 *  Getter and Setters
	 * --------------------
	 */
	
	public ServerSocket getSrvSocket() {
		return this.srvSocket;
	}
	
	public Socket getSocket() {
		return this.socket;
	}
	
	public InetAddress getAddress() {
		return this.address;
	}
	
	public boolean getClosedConnection() {
		return closedConnection;
	}
	
	
	/*
	 * ------------------
	 *  Receive and Send
	 * ------------------
	 */
	public String receiveMessage() throws IOException {
		return this.in.readLine();
	}
	
	public void sendMessage(String message) {
		this.out.println(message);
	}
	
	public void closeConnection() throws IOException {
		if( this.srvSocket != null )
			this.srvSocket.close();
		this.socket.shutdownOutput();
		this.closedConnection = true;
	}
}
