package main;

public class BankAccount {

    private double balance;

    public BankAccount() {
        this.balance = 0;
    }

    public void deposit(double amount) {
        if(amount > 0) {
            this.balance += amount;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void withdraw(double withdrawAmount) {
        if(this.balance - withdrawAmount < 0) {
            throw new IllegalArgumentException(); 
        }
        this.balance = this.balance - withdrawAmount; 
        // subtract the withdraw amount from the actual account. 
        
    }


    public double getBalance() {
        return this.balance;
    }

}


