package main;

public class BankAccount {

    private double balance;
    private boolean closed;

    public BankAccount() {
        this.balance = 0;
        this.closed = false;
    }

    public void deposit(double amount) {
        if(amount > 0 && !this.closed) {
            this.balance += amount;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void withdraw(double withdrawAmount) {
        if(this.balance - withdrawAmount < 0 || this.closed) {
            throw new IllegalArgumentException(); 
        }
        this.balance = this.balance - withdrawAmount; 
        // subtract the withdraw amount from the actual account. 
        
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




