package main;

import java.util.ArrayList;

public class BankAdmin {

    private String password;
    
    public BankAdmin(String password){
        this.password = password;
    }

    public boolean checkPassword(String password){
        return this.password.equals(password);
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

    public boolean freezeAccount(BankAccount account){
        if (account.isClosed()){
            return false;
        }
        account.closeAccount();
        return true;
    }

    public boolean unfreezeAccount(BankAccount account){
        if (!account.isClosed()){
            return false;
        }
        account.reopenAccount();
        return true;
    }

    public BankAccount getMaximum(ArrayList<BankAccount> accounts){
        double max = -1;
        BankAccount maxAccount = null;
        for (BankAccount account : accounts){
            double balance = account.getBalance();
            if (balance > max){
                max = balance;
                maxAccount = account;
            }
        }
        return maxAccount;
    }

    public BankAccount getMinimum(ArrayList<BankAccount> accounts){
        double min = Double.MAX_VALUE;
        BankAccount minAccount = null;
        for (BankAccount account : accounts){
            double balance = account.getBalance();
            if (balance < min){
                min = balance;
                minAccount = account;
            }
        }
        return minAccount;
    }

    public double getTotal(ArrayList<BankAccount> accounts){
        double total = 0;
        for (BankAccount account : accounts){
            double balance = account.getBalance();
            total += balance;
        }
        return total;
    }

    public double getAverage(ArrayList<BankAccount> accounts){
        double total = getTotal(accounts);
        int numAccounts = accounts.size();
        if (numAccounts == 0){
            return 0;
        }
        return total/numAccounts;
    }


    

}