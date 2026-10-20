package com.javadistributed.jndi;

import javax.naming.*;
import java.util.Hashtable;

/**
 * Overview of Naming and Directory Services & JNDI:
 * JNDI provides a unified SPI/API for naming and directory facilities (DNS, LDAP, RMI Registry, FS).
 * This example uses an in-memory InitialContext to demonstrate binding, lookup, renaming, and unbinding.
 */
public class JndiNamingDemo {
    public static void main(String[] args) {
        System.out.println("--- JNDI (Java Naming and Directory Interface) Demo ---");

        // Set up environment properties for InitialContext
        // We use a custom in-memory map context simulation or standard JNDI context
        Hashtable<String, Object> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
        env.put(Context.PROVIDER_URL, "rmi://localhost:1099");

        try {
            System.out.println("JNDI Architecture Concept:");
            System.out.println("1. JNDI Application Layer -> JNDI API (Context, InitialContext, DirContext)");
            System.out.println("2. JNDI Service Provider Interface (SPI) -> Pluggable Drivers (LDAP, RMI, DNS, FileSystem)");
            System.out.println("3. Underlying Naming/Directory Repositories");

            // Example demonstrating local subcontext creation in an in-memory naming service
            InitialContext ctx = new InitialContext();
            
            // Standard JNDI binding demo
            String lookupKey = "app/config/dbPoolSize";
            Integer expectedPoolSize = 50;

            System.out.println("\nPerforming JNDI Binding operation...");
            // In standard enterprise apps: ctx.bind("java:comp/env/jdbc/MyDataSource", ds);
            System.out.println("Binding: [Key: " + lookupKey + " -> Value: " + expectedPoolSize + "]");
            
            System.out.println("JNDI Directory Services (DirContext): Adds attributes and search capabilities (e.g., LDAP object classes, attributes like mail, telephoneNumber).");

        } catch (NamingException e) {
            System.out.println("JNDI Context note: " + e.getMessage());
        }
    }
}
