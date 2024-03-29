package information;

/**
 * 
 * Class that builds the peer's information
 *
 */
public class PeerInfo {
	public static PeerInfo peerInfo;			//Peer's information
	private final String access_point;			//Access point
	
	private final int server_ID;				//Server's ID
	
	/**
	 * PeerInfo's constructor
	 * @param version_protocol Protocol's version
	 * @param access_point Access Point
	 * @param server_ID Server's ID
	 */
	private PeerInfo(String version_protocol, String access_point, int server_ID) {
		try {
			Version.createVersion(version_protocol);
		} catch (Exception ignore) {
		}
		this.access_point = access_point;
		this.server_ID = server_ID;
	}
	
	/**
	 * Creates the peer's information
	 * @param version_protocol Protocol's version
	 * @param access_point Access point
	 * @param server_ID Server's ID
	 */
	public static void createPeerInfo(String version_protocol, String access_point, int server_ID) throws Exception {
		if( peerInfo != null )
			throw new Exception("More than one instatiation of a singleton class");
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
}
