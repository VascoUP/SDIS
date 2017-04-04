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
            registry = LocateRegistry.createRegistry(1099);//use any no. less than 55000
	        UnicastRemoteObject.unexportObject(registry, true);
	        
            registry.list();
            // This call will throw an exception if the registry does not already exist\
			registry.unbind(PeerInfo.peerInfo.getAccessPoint());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ;
		}
	}
	
	@Override
	public void run(String[] args) throws RemoteException {
		System.out.print("\n");
		RMIRunner.parseArgs(args);
	}
}
