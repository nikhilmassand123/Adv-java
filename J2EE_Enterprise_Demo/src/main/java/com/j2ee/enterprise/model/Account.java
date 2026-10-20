package com.j2ee.enterprise.model;

import java.io.Serializable;

/**
 * Enterprise Application Concept: Domain / Entity Object
 * Represents an account entity residing across tiers.
 */
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    private String accountNumber;
    private String ownerName;
    private double balance;

    public Account(String accountNumber, String ownerName, double balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public double getBalance() { return balance; }

    public void credit(double amount) { this.balance += amount; }
    public boolean debit(double amount) {
        if (amount <= balance) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", balance=" + balance +
                '}';
    }
}
