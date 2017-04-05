import rmi.Instructable;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

class TestApp {
	private static TestApp testApp;
	private static Instructable stub;
	
	/*==============
	 * MAIN METHODS
	 *==============
	 */
    public static void main(String[] args) {
    	parseArgs(args);
    }
	private static void parseArgs(String[] args) {
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
    private TestApp() {
    }

    private boolean getRMI(String peer_name) {
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
    
    private void registerRMI(String peer_name) throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry();
        stub = (Instructable) registry.lookup(peer_name);
    }
    
    private void runRMI(String[] rmiArgs) {
    	try {
			stub.run(rmiArgs);
		} catch (RemoteException ignored) {
		}
    }
}
