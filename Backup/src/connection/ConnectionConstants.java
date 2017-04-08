package connection;

public class ConnectionConstants {
	public static final int PACKET_SIZE = 64000; 			//Packet maximum size (without header)
	public static final int PACKET_SIZE_OVERHEAD = 64256; 	//Packet size with header
	
	public static final int MC_GROUP_PORT = 5000; 			//MC's group port
	public static final int MDB_GROUP_PORT = 5001; 			//MDB's group port
	public static final int MDR_GROUP_PORT = 5002;			//MDR's group port
	
	public static final String MC_GROUP = "225.4.5.6";		//MC's group
	public static final String MDB_GROUP = "226.4.5.6";		//MDB's group
	public static final String MDR_GROUP = "227.4.5.6";		//MDR's group
}
