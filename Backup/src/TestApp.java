import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rmi.Instructable;

/**
 * 
 * Class that creates the test application to test all protocols
 *
 */
public class TestApp {	
	private static TestApp testApp;
	
	/*
	 *==============
	 * MAIN METHODS
	 *==============
	 */
	
	/**
	 * Main function to execute the test application
	 * @param args Arguments passed by the terminal/command line
	 */
    public static void main(String[] args) {
    	if(args.length < 2 || args.length > 4){
    		System.out.println("\njava TestApp <peer_ap> <sub_protocol> <opnd_1> <opnd_2> ");
    		return;
    	}
    	parseArgs(args);
    }
    
    /**
     * This function parses the arguments passed by the terminal/command line
     * @param args Arguments passed by the terminal/command line
     */
	public static void parseArgs(String[] args) {    	
    	String[] rmiArgs = new String[args.length - 1];
    	System.arraycopy(args, 1, rmiArgs, 0, args.length - 1);
    	
    	//Initiates the TestApp
    	testApp = new TestApp();
    	if (testApp.getRMI(args[0]) )
    		testApp.runRMI(rmiArgs);    	
    }
	
	/**
	 * RMI registry
	 */
	private Registry registry;
    
	/**
	 * Interface to RMI instructable
	 */
    private Instructable stub;
    
    
    /*
     *====================
     * NON STATIC METHODS
     *====================
     */
    
    /**
     * TestaApp constructor
     */
    public TestApp() {
    }

    /**
     * Creates the RMI with the peer's name
     * @param peer_name Name of the peer which will be registered
     * @return false if the registry catches some exception or true otherwise 
     */
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
    
    /**
     * Registers the peer in RMI
     * @param peer_name Name of the peer which will be registered
     * @throws RemoteException To a number of communication-related exceptions that may occur during the execution of a remote method call
     * @throws NotBoundException This is thrown if an attempt is made to lookup or unbind in the registry a name that has no associated binding
     */
    public void registerRMI(String peer_name) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry();
        stub = (Instructable) registry.lookup(peer_name);
    }
    
    /**
     * Runs the RMI 
     * @param rmiArgs Arguments used to run the RMI
     */
    public void runRMI(String[] rmiArgs) {
    	try {
    		String result = stub.run(rmiArgs);
        	System.out.println(result);
		} catch (RemoteException ignore) {
		}
    }
}
