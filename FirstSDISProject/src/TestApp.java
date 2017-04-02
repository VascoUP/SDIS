import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rmi.Instructable;

public class TestApp {	
	private static TestApp testApp;
	
	private Registry registry;
	private Instructable stub;
	
	
	/*==============
	 * MAIN METHODS
	 *==============
	 */
    public static void main(String[] args) {
    	parseArgs(args);
    }
    
    public static void parseArgs(String[] args) {    	
    	String[] rmiArgs = new String[args.length - 1];
    	System.arraycopy(args, 1, rmiArgs, 0, args.length - 1);
    	
    	testApp = new TestApp();
    	if (testApp.getRMI(args[0]) )
    		testApp.runRMI(rmiArgs);    	
    }
    
    
    /*====================
     * NON STATIC METHODS
     *====================
     */
    public TestApp() {
    }

    public boolean getRMI(String peer_name) {
        try {
			testApp.registerRMI(peer_name);
		} catch (RemoteException e) {
			System.out.println("Remote exception");
			return false;
		} catch (NotBoundException e) {
			System.out.println("Not bound exception");
			return false;
		}
        
        return true;
    }
    
    public void runRMI(String[] rmiArgs) {
    	try {
			stub.run(rmiArgs);
		} catch (RemoteException e) {
		}
    }
    
    public void registerRMI(String peer_name) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry();
        stub = (Instructable) registry.lookup(peer_name);
    }
}
