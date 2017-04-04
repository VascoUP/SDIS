package rmi;

public class RMIStorage {

	private static RMIObject rmi;
	
	public static RMIObject getRMI() {
		return rmi;
	}
	
	public static void initRMI() {
		rmi = new RMIObject();
	}
}
