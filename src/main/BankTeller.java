package main;

public class BankTeller {

    private String password;

    public BankTeller(String password) {
        this.password = password;
    }

    public boolean checkPassword(String input) {
        return this.password.equals(input);
    }

    public void approveTransaction(BankAccount account, Transaction transaction) {
        if (!transaction.isPending()) {
            throw new IllegalStateException("Transaction is not pending.");
        }
        account.applyApprovedTransaction(transaction);
    }

    public void denyTransaction(Transaction transaction) {
        if (!transaction.isPending()) {
            throw new IllegalStateException("Transaction is not pending.");
        }
        transaction.deny();
    }
}