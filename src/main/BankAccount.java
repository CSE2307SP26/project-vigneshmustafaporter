package main;

import java.util.ArrayList;

public class BankAccount {

    private double balance;
    private ArrayList<Double> transactions;
    private static int accountID = 0;

    public BankAccount() {
        this.balance = 0;
        this.transactions = new ArrayList<Double>();
        BankAccount.accountID++;
    }

    public int getID() {
        return BankAccount.accountID;
    }

    public void deposit(double amount) {
        if(amount > 0) {
            this.balance += amount;
            record(amount);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void withdraw(double withdrawAmount) {
        if(this.balance - withdrawAmount < 0) {
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
        return this.balance;
    }

}


