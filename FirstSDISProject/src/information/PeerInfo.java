package information;

public class PeerInfo {
	public static PeerInfo peerInfo;
	
	public static void createPeerInfo(String version_protocol, String access_point, int server_ID) {
		if( peerInfo != null )
			throw new Error("More than one instatiation of a singleton class");
		peerInfo = new PeerInfo(version_protocol, access_point, server_ID);
	}
	private final String version_protocol;
	private final String access_point;
	
	private final int server_ID;
	
	private PeerInfo(String version_protocol, String access_point, int server_ID) {
		this.version_protocol = version_protocol;
		this.access_point = access_point;
		this.server_ID = server_ID;
	}

	public String getAccessPoint() {
		return access_point;
	}

	public int getServerID() {
		return server_ID;
	}

	public String getVersionProtocol() {
		return version_protocol;
	}
}
