package information;

/**
 * 
 * Class that builds the peer's information
 *
 */
public class PeerInfo {
	public static PeerInfo peerInfo;			//Peer's information
	private final String version_protocol;		//Protocol's version
	private final String access_point;			//Access point
	private int capacity = Integer.MAX_VALUE;	//Peer's capacity
	
	private final int server_ID;				//Server's ID
	
	/**
	 * PeerInfo's constructor
	 * @param version_protocol Protocol's version
	 * @param access_point Access Point
	 * @param server_ID Server's ID
	 */
	private PeerInfo(String version_protocol, String access_point, int server_ID) {
		this.version_protocol = version_protocol;
		this.access_point = access_point;
		this.server_ID = server_ID;
	}
	
	/**
	 * Creates the peer's information
	 * @param version_protocol Protocol's version
	 * @param access_point Access point
	 * @param server_ID Server's ID
	 */
	public static void createPeerInfo(String version_protocol, String access_point, int server_ID) {
		if( peerInfo != null )
			throw new Error("More than one instatiation of a singleton class");
		peerInfo = new PeerInfo(version_protocol, access_point, server_ID);
	}

	/**
	 * Gets the access point
	 * @return The access point
	 */
	public String getAccessPoint() {
		return access_point;
	}

	/**
	 * Gets the server's ID
	 * @return The server's ID
	 */
	public int getServerID() {
		return server_ID;
	}

	/**
	 * Gets the protocol's version
	 * @return The protocol's version
	 */
	public String getVersionProtocol() {
		return version_protocol;
	}
	
	/**
	 * Gets the peer's capacity
	 * @return The peer's capacity
	 */
	public int getCapacity() {
		return capacity;
	}
	
	/**
	 * Sets the peer's capacity
	 * @param capacity Peer's new capacity
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
}
