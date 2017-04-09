package information;

/**
 * 
 * This class builds the different program's versions
 *
 */
public class Version {
	public static Version instance;							//Instance of the class Version
	
	private static final String BASIC_PROTOCOL = "1.0";		//Basic protocol (version 1.0)
	
	private final String version_protocol;					//Protocol's version
	
	/**
	 * Version's constructor
	 * @param version The protocol's version
	 */
	private Version(String version) {
		version_protocol = version;
	}

	/**
	 * Creates the new program's version
	 * @param version The new program's version
	 * @throws Exception The class Exception and its subclasses are a form of Throwable that indicates conditions that a reasonable application might want to catch
	 */
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
	
	/**
	 * Verifies if a protocol was enhanced
	 * @return true if the protocol is enhanced, false otherwise
	 */
	public static boolean isEnhanced() {
		return !Version.instance.getVersionProtocol().equals(BASIC_PROTOCOL);
	}
}
