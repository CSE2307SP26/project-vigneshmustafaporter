package main;



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

}