package main;

import java.util.ArrayList;

public class BankAccount {

    private double balance;
    private boolean closed;
    private ArrayList<Double> transactions;
    private int ID;
    private static int accountIDs = 0;
    public String name;
    private String password;

    public BankAccount(String name, String password) {
        this.name = name;
        this.password = password;
        this.balance = 0;
        this.transactions = new ArrayList<Double>();
        this.closed = false;
        this.ID = ++BankAccount.accountIDs;
    }
    
    public boolean checkPassword(String input) {
        return this.password.equals(input);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getID() {
        return this.ID;
    }

    public String getName() {
        return this.name;
    }

    public void deposit(double amount) {
        if (isClosed()) {
            throw new IllegalStateException("Account is closed.");
        }
        if(amount > 0 && !this.closed) {
            this.balance += amount;
            record(amount);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void withdraw(double withdrawAmount) {
        if (isClosed()) {
            throw new IllegalStateException("Account is closed.");
        }
        if (withdrawAmount < 0) {
            throw new IllegalArgumentException("Invalid withdraw amount.");
        }
        if(this.balance - withdrawAmount < 0 || this.closed) {
            throw new IllegalArgumentException(); 
        }
        this.balance = this.balance - withdrawAmount; 
        record(-1 * withdrawAmount);
        // subtract the withdraw amount from the actual account. 
        
    }

    public void record(double amount) {
        transactions.add(amount);
    }

    public ArrayList<Double> getTransactions() {
        return this.transactions;
    }

    public double getBalance() {
        if (this.closed) {
            throw new IllegalStateException();
        }
        return this.balance;
    }

    public void renameAccount(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Invalid account name.");
        }
        this.name = newName;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public void closeAccount() {
        if (this.closed) {
            throw new IllegalStateException();
        }
        this.closed = true;
    }

    public void reopenAccount() {
        if (!isClosed()) {
            throw new IllegalStateException("Account is already open.");
        }
        this.closed = false;
    }

    public void transferTo(BankAccount otherAccount, double amount) {
        if (otherAccount == null || otherAccount.isClosed() || this.closed || amount <= 0) {
            throw new IllegalArgumentException();
        }
        this.withdraw(amount);
        otherAccount.deposit(amount);
    }
}




