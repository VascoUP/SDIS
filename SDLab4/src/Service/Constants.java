package Service;

public class Constants {
	public static final int packetSize = 1024;
	public static final int port = 1025;
	
	public static final String serverArg = "server";
	public static final String clientArg = "client";
	
	public static final int serverArgsSize = 2;
	public static final int clientMinArgsSize = 5;
	
	public static final int maxRepeats = 3;
	
	public static final String serverUsage = "Usage server: java Echo server";
	public static final String clientUsage = "Usage client: java Echo client <hostname> <string to echo>";
	
	public static final String registerMessage = "reg";
	public static final String lookupMessage = "look";
	public static final String endMessage = "finalPacket";
	public static final String wrongArgumentsMessage = "wrongArgs";
	
	public static final String errorMessage = "error";
	public static final String successMessage = "success";
	
	public static final String divideRegex = ":";
}
