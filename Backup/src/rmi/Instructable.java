package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Instructable extends Remote {
	public String run(String[] args) throws RemoteException;
}
