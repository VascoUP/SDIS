package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * This interface builds rmi's instructable that extends the Remote class
 *
 */
public interface Instructable extends Remote {
	public String run(String[] args) throws RemoteException;
}
