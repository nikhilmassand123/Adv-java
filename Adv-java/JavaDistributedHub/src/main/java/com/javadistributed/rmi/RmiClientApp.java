package com.javadistributed.rmi;

import com.javadistributed.model.UserProfile;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiClientApp {
    public static void main(String[] args) {
        try {
            // Locate registry running on host and port
            Registry registry = LocateRegistry.getRegistry("localhost", RmiServerApp.RMI_PORT);

            // Lookup remote stub by name
            System.out.println("[RMI Client] Looking up '" + RmiServerApp.BINDING_NAME + "' from registry...");
            RemoteInventoryService service = (RemoteInventoryService) registry.lookup(RmiServerApp.BINDING_NAME);

            // Invoke remote methods
            String status = service.checkStatus();
            System.out.println("[RMI Client] Server status: " + status);

            System.out.println("[RMI Client] Available services: " + service.getAvailableServices());

            // Pass serialized object across JVM boundary
            UserProfile admin = new UserProfile(501, "sarah_admin", "ADMIN", "temp_token");
            boolean authorized = service.authenticateAndLog(admin);
            System.out.println("[RMI Client] Invocation with UserProfile authorized: " + authorized);

        } catch (Exception e) {
            System.err.println("[RMI Client] Exception during invocation: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
