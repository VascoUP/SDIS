package rmi;

public class RMIStorage {

	private static RMIObject rmi;
	
	public static void initRMI() {
		rmi = new RMIObject();
	}
	
	public static RMIObject getRMI() {
		return rmi;
	}
}
