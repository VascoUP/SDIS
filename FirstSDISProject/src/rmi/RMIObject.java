package rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import information.PeerInfo;

public class RMIObject implements Instructable {

	public RMIObject() {
		
	}
	
	public void bind() {		
        try {
        	Instructable stub = (Instructable) UnicastRemoteObject.exportObject(this, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();//use any no. less than 55000
            registry.list();
            
            registry.rebind(PeerInfo.peerInfo.getAccessPoint(), stub);

            System.err.println("RMI object ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
            System.exit(-1);
        }
	}
	
	public void unbind() {
        Registry registry = null;
        try {
        	try {
	            registry = LocateRegistry.getRegistry(52365);//use any no. less than 55000
	            registry.list();
	            // This call will throw an exception if the registry does not already exist
	        }
	        catch (RemoteException e) { 
	            registry = LocateRegistry.createRegistry(52365);
	        }
        	
			registry.unbind(PeerInfo.peerInfo.getAccessPoint());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(String[] args) throws RemoteException {
		for( int i = 0; i < args.length; i++ )
			System.out.println(args[i]);
		RMIRunner.parseArgs(args);
	}
}
