package main;

import java.util.ArrayList;

public class BankAccount {

    private double balance;
    private boolean closed;
    private ArrayList<Double> transactions;
    private int ID;
    private static int accountIDs = 0;

    public BankAccount() {
        this.balance = 0;
        this.transactions = new ArrayList<Double>();
        this.closed = false;
        this.ID = ++BankAccount.accountIDs;
    }

    public int getID() {
        return this.ID;
    }

    public void deposit(double amount) {
        if(amount > 0 && !this.closed) {
            this.balance += amount;
            record(amount);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void withdraw(double withdrawAmount) {
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

    public boolean isClosed() {
        return this.closed;
    }

    public void closeAccount() {
        if (this.closed) {
            throw new IllegalStateException();
        }
        this.closed = true;
    }

    public void transferTo(BankAccount otherAccount, double amount) {
        if (otherAccount == null || otherAccount.isClosed() || this.closed || amount <= 0) {
            throw new IllegalArgumentException();
        }
        this.withdraw(amount);
        otherAccount.deposit(amount);
    }
}




