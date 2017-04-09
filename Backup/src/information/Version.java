package information;

public class Version {
	public static Version instance;
	
	private final String version_protocol;		//Protocol's version
	
	private Version(String version) {
		version_protocol = version;
	}

	public static void createVersion(String version) throws Exception {
		if(instance != null)
			throw new Exception("More than one instatiation of a singleton class");
		instance = new Version(version);
	}

	/**
	 * Gets the protocol's version
	 * @return The protocol's version
	 */
	public String getVersionProtocol() {
		return version_protocol;
	}
}
