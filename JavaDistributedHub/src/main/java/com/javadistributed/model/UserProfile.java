package com.javadistributed.model;

import java.io.Serializable;

/**
 * Serializable class demonstrating Java Object Serialization.
 * Implements Serializable to allow state to be flattened into a byte stream
 * and reconstituted across JVM boundaries or stored on disk.
 */
public class UserProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    private int userId;
    private String username;
    private String role;

    // Transient fields are skipped during serialization
    private transient String sessionToken;

    public UserProfile(int userId, String username, String role, String sessionToken) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.sessionToken = sessionToken;
    }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
    public String getSessionToken() { return sessionToken; }

    @Override
    public String toString() {
        return "UserProfile{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", sessionToken='" + sessionToken + "' (transient)}" +
                '}';
    }
}
