package main;



public class BankAdmin {
    
    public BankAdmin(){

    }

    public void depositInterest(BankAccount account, double amount) {
        if(amount > 0) {
            account.deposit(amount);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void collectFees(BankAccount account, double amount) {
        if(amount > 0) {
            try {
                account.withdraw(amount);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
}