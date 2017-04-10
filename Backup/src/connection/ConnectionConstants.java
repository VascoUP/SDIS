package connection;

/**
 * 
 * This class creates the main constants to be used in this program
 * 
 */
public class ConnectionConstants {
	public static final int PACKET_SIZE = 64000; 			//Packet maximum size (without header)
	public static final int PACKET_SIZE_OVERHEAD = 64256; 	//Packet size with header
	
	public static int MC_GROUP_PORT; 						//MC's group port
	public static int MDB_GROUP_PORT; 						//MDB's group port
	public static int MDR_GROUP_PORT;						//MDR's group port
	
	public static String MC_GROUP;							//MC's group
	public static String MDB_GROUP;							//MDB's group
	public static String MDR_GROUP;							//MDR's group
	
	/**
	 * Initiates the main values
	 * @param MCPORT MC's group port
	 * @param MCGROUP MC's group
	 * @param MDBPORT MDB's group port
	 * @param MDBGROUP MDB's group
	 * @param MDRPORT MDR's group port
	 * @param MDRGROUP MDR's group
	 */
	public static void createValues(int MCPORT, String MCGROUP, int MDBPORT, String MDBGROUP, int MDRPORT, String MDRGROUP){
		MC_GROUP_PORT = MCPORT;
		MC_GROUP = MCGROUP;
		MDB_GROUP_PORT = MDBPORT;
		MDB_GROUP = MDBGROUP;
		MDR_GROUP_PORT = MDRPORT;
		MDR_GROUP = MDRGROUP;
	}
}
