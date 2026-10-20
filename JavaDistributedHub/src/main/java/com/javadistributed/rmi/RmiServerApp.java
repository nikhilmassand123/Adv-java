package com.javadistributed.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiServerApp {
    public static final String BINDING_NAME = "InventoryService";
    public static final int RMI_PORT = 1099;

    public static void main(String[] args) {
        try {
            // Programmatically spin up the RMI Registry on port 1099
            Registry registry = LocateRegistry.createRegistry(RMI_PORT);
            System.out.println("[RMI Server] RMI Registry started on port " + RMI_PORT);

            // Instantiate remote servant object
            RemoteInventoryService service = new RemoteInventoryServiceImpl();

            // Bind the remote reference in the naming registry
            registry.rebind(BINDING_NAME, service);
            System.out.println("[RMI Server] Service bound under name: '" + BINDING_NAME + "'");
            System.out.println("[RMI Server] Ready for incoming remote method invocations...");
        } catch (Exception e) {
            System.err.println("[RMI Server] Error starting RMI server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
