package rmi;

/**
 * 
 * This class builds a RMI's storage
 *
 */
public class RMIStorage {

	private static RMIObject rmi;		//RMI's object
	
	/**
	 * Gets the RMI's object
	 * @return The RMI's object
	 */
	public static RMIObject getRMI() {
		return rmi;
	}
	
	/**
	 * Initializes the RMI's object
	 */
	public static void initRMI() {
		rmi = new RMIObject();
	}
}
