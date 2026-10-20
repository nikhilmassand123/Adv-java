package com.javadistributed.rmi;

import com.javadistributed.model.UserProfile;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Remote Interface:
 * Must extend java.rmi.Remote and every remote method must throw java.rmi.RemoteException.
 */
public interface RemoteInventoryService extends Remote {
    String checkStatus() throws RemoteException;
    boolean authenticateAndLog(UserProfile profile) throws RemoteException;
    List<String> getAvailableServices() throws RemoteException;
}
