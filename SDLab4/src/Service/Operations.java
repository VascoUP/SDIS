package Service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Operations extends Remote {
	int register(String name, String plate_no) throws RemoteException;
	String lookup(String plate_no) throws RemoteException;
}
