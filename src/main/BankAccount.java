package main;

import java.util.ArrayList;

public class BankAccount {

    private double balance;
    private boolean closed;
    private ArrayList<Transaction> transactions;
    private int ID;
    private static int accountIDs = 0;
    public String name;
    private String password;

    public BankAccount(String name, String password) {
        this.name = name;
        this.password = password;
        this.balance = 0;
        this.transactions = new ArrayList<Transaction>();
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
        if (amount <= 0) {
            throw new IllegalArgumentException();
        }
        transactions.add(new Transaction(amount, Transaction.Type.DEPOSIT));
    }

    public void withdraw(double withdrawAmount) {
        if (isClosed()) {
            throw new IllegalStateException("Account is closed.");
        }
        if (withdrawAmount < 0) {
            throw new IllegalArgumentException("Invalid withdraw amount.");
        }
        if (this.balance - withdrawAmount < 0) {
            throw new IllegalArgumentException();
        }
        transactions.add(new Transaction(withdrawAmount, Transaction.Type.WITHDRAWAL));
    }

    public void directDeposit(double amount) {
        if (isClosed()) {
            throw new IllegalStateException("Account is closed.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException();
        }
        Transaction transaction = new Transaction(amount, Transaction.Type.TRANSFER_IN);
        this.balance += amount;
        transaction.transfer();
        transactions.add(transaction);
    }

    public void directWithdraw(double amount) {
        if (isClosed()) {
            throw new IllegalStateException("Account is closed.");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Invalid withdraw amount.");
        }
        if (this.balance - amount < 0) {
            throw new IllegalArgumentException();
        }
        Transaction transaction = new Transaction(amount, Transaction.Type.TRANSFER_OUT);
        this.balance -= amount;
        transaction.transfer();
        transactions.add(transaction);
    }

    void applyApprovedTransaction(Transaction transaction) {
        if (transaction.getType() == Transaction.Type.DEPOSIT) {
            this.balance += transaction.getAmount();
        } else {
            if (this.balance < transaction.getAmount()) {
                throw new IllegalStateException("Insufficient funds to approve withdrawal.");
            }
            this.balance -= transaction.getAmount();
        }
        transaction.approve();
    }

    public ArrayList<Transaction> getTransactions() {
        return this.transactions;
    }

    public void addTransactionNote(int index, String note) {
        validateTransactionIndex(index);
        transactions.get(index).setNote(note);
    }

    public String getTransactionNote(int index) {
        validateTransactionIndex(index);
        return transactions.get(index).getNote();
    }

    private void validateTransactionIndex(int index) {
        if (index < 0 || index >= transactions.size()) {
            throw new IllegalArgumentException("Invalid transaction index.");
        }
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
        this.directWithdraw(amount);
        otherAccount.directDeposit(amount);
    }
}