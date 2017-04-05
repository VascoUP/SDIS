package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Instructable extends Remote {
	void run(String[] args) throws RemoteException;
}
