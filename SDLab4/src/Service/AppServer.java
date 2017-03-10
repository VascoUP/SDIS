package Service;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.Vector;
import Vehicle.RegisterVehicle;

public class AppServer implements Operations {

	private String remote_object_name;
	private Vector<RegisterVehicle> vehicles;
	
	
	public AppServer(String rom) {    	
    	this.vehicles = new Vector<RegisterVehicle>();
    	this.remote_object_name = rom;
    }
	
	public void connect() {
        try {
        	Operations stub = (Operations) UnicastRemoteObject.exportObject(this, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(remote_object_name, stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
            System.exit(-1);
        }
	}

	@Override
	public int register(String name, String plate_no) throws RemoteException {
		RegisterVehicle vehicle = new RegisterVehicle(name, plate_no);
		int returnSize = -1;
		
    	if( this.vehicles.indexOf(vehicle) == -1 ) {
        	this.vehicles.addElement(vehicle);
        	returnSize = this.vehicles.size();
    	}
    	
    	System.out.println("Register : " + name + " - " + plate_no + "; Return : " + returnSize);
    	
    	return returnSize;
	}
	
	@Override
    public String lookup(String plate_no) {
    	Iterator<RegisterVehicle> it = vehicles.iterator();
    	String returnString = "NOT_FOUND";

    	while(it.hasNext()) {
    		RegisterVehicle vehicle = it.next();
    		
    		System.out.println(vehicle.getPlate_no());
    		
    		if( vehicle.getPlate_no().equals(plate_no) ) {
    			returnString = vehicle.getName();
    			break;
    		}
    	}
    	
    	System.out.println("Lookup : " + plate_no + "; Return : " + returnString);
    	
    	return returnString;
    }
    
}
