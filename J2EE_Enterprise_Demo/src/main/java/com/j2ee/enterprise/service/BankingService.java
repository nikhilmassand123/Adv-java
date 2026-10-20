package com.j2ee.enterprise.service;

import com.j2ee.enterprise.model.Account;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simulates the Business Tier (Session Bean / Service Layer) in J2EE architecture.
 * Manages business logic, transaction boundaries, and data tier access.
 */
public class BankingService {
    // In-memory data store representing Enterprise Information System (EIS / DB Tier)
    private final Map<String, Account> database = new ConcurrentHashMap<>();

    public BankingService() {
        // Seed database
        database.put("ACC1001", new Account("ACC1001", "Alice Johnson", 5400.00));
        database.put("ACC1002", new Account("ACC1002", "Bob Smith", 12500.50));
        database.put("ACC1003", new Account("ACC1003", "Charlie Davis", 890.25));
    }

    public Account findAccount(String accNo) {
        return database.get(accNo);
    }

    public synchronized boolean transfer(String fromAcc, String toAcc, double amount) {
        Account sender = database.get(fromAcc);
        Account receiver = database.get(toAcc);

        if (sender != null && receiver != null && sender.debit(amount)) {
            receiver.credit(amount);
            return true; // Commit transaction
        }
        return false; // Rollback
    }

    public Map<String, Account> listAccounts() {
        return database;
    }
}
