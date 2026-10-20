package com.javadistributed.rmi;

import com.javadistributed.model.UserProfile;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.List;

/**
 * Remote Object Implementation:
 * Extends UnicastRemoteObject to establish point-to-point remote references over TCP.
 */
public class RemoteInventoryServiceImpl extends UnicastRemoteObject implements RemoteInventoryService {

    public RemoteInventoryServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String checkStatus() throws RemoteException {
        return "RMI Service is ONLINE on port 1099";
    }

    @Override
    public boolean authenticateAndLog(UserProfile profile) throws RemoteException {
        System.out.println("[RMI Server] Remote call received with serialized object: " + profile);
        return profile != null && profile.getRole().equalsIgnoreCase("ADMIN");
    }

    @Override
    public List<String> getAvailableServices() throws RemoteException {
        return Arrays.asList("OrderProcessing", "BillingEngine", "AnalyticsPipeline");
    }
}
