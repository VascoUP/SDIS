package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Instructable extends Remote {
	public void run(String[] args) throws RemoteException;
}
