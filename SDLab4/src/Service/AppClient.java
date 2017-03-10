package Service;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author up201403485
 * @description 05
 */

public class AppClient {		
	
	private String remote_object_name;
	private String host_name;
	private String operation;
	private String[] operations;
	
    
    public AppClient(String hostName, String rom, String operation, String[] arguments) {
    	this.host_name = hostName;
    	this.remote_object_name = rom;
    	
    	this.operation = operation;
    	this.operations = arguments;
    }
	
	public void connect() {
		Registry registry;
		Operations stub;
		
        try {
            registry = LocateRegistry.getRegistry(host_name);
            stub = (Operations) registry.lookup(remote_object_name);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
            return ;
        }
        
        sendMessages(stub);
	}
    
    public void sendMessages(Operations stub) {
    	if( (operation.equals(Constants.registerMessage) && operations.length % 2 != 0) ||
			(!operation.equals(Constants.lookupMessage) && !operation.equals(Constants.registerMessage)) ) {
    		throw new Error("Wrong arguments " + operation);
    	}
    		
    	for( int i = 0; i < operations.length; i++ )
    		i = sendMessage(stub, i);
    }
        
    public int sendMessage(Operations stub, int index) {
    	int nI = index;
    	
    	if(operation.equals(Constants.lookupMessage)) {
    		try {
				stub.lookup(this.operations[nI]);
			} catch (RemoteException e) {
	            System.err.println("Client exception: " + e.toString());
	            e.printStackTrace();
			}
    	} else if(operation.equals(Constants.registerMessage)) {
    		try {
				stub.register(this.operations[nI++], this.operations[nI]);
			} catch (RemoteException e) {
	            System.err.println("Client exception: " + e.toString());
	            e.printStackTrace();
			}
    	}
    	
    	return nI;
    }    
}
