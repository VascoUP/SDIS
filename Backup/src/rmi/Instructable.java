package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * This class builds rmi's instructable that extends the Remote class
 *
 */
public interface Instructable extends Remote {
	public void run(String[] args) throws RemoteException;
}
