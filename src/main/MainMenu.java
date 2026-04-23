package main;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;

public class MainMenu {

    private static final int EXIT_SELECTION = 18;
    private static final int MAX_SELECTION = 18;
    private static final int ADMIN_EXIT_SELECTION = 7;
    private static final int ADMIN_MAX_SELECTION = 7;
    private static final int TELLER_EXIT_SELECTION = 2;
    private static final int TELLER_MAX_SELECTION = 2;
    private ArrayList<BankAccount> userAccounts;
    private ArrayList<ReportedAccount> reportedAccounts;
    private BankAccount currentAccount;
    private Scanner keyboardInput;
    private BankAdmin admin;
    private BankTeller teller;
    private FinancialProfileCollector profileCollector;
    private FinancialProfile finprofile;

    public MainMenu() {
        this.keyboardInput = new Scanner(System.in);
        this.userAccounts = new ArrayList<BankAccount>();
        this.reportedAccounts = new ArrayList<ReportedAccount>();
        System.out.println("Account Setup:");
        createAccount();
        this.currentAccount = userAccounts.get(0);
        this.admin = null;
        this.teller = null;
        this.profileCollector = new FinancialProfileCollector(keyboardInput);
    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App! You are currently using account " + this.currentAccount.getID() + ": " + this.currentAccount.getName());
        System.out.println("1. Make a deposit");
        System.out.println("2. Make a withdraw");
        System.out.println("3. Check your balance");
        System.out.println("4. Check transaction history");
        System.out.println("5. Create additional account");
        System.out.println("6. Switch account");
        System.out.println("7. Rename current account");
        System.out.println("8. Close your account");
        System.out.println("9. Reopen an account");
        System.out.println("10. Transfer money");
        System.out.println("11. Enter admin controls");
        System.out.println("12. Create a financial profile");
        System.out.println("13. Use the home affordability calculator");
        System.out.println("14. Update password");
        System.out.println("15. Use the Loan Calculator"); 
        System.out.println("16. Use the Savings Goal Calculator");
        System.out.println("17. Enter bank teller mode");
        System.out.println("18. Exit the app");
    }

    public int getUserSelection(int max) {
        int selection = -1;
        while(selection < 1 || selection > max) {
            System.out.print("Please make a selection: ");
            try {
                selection = keyboardInput.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter a number between 1 and " + max + ".");
                keyboardInput.nextLine();
            }
        }
        return selection;
    }

    public void processInput(int selection) {
        switch (selection) {
            case 1:
                performDeposit();
                break;
            case 2:
                performWithDraw();
                break;
            case 3:
                checkBalance();
                break;
            case 4:
                viewTransactions();
                break;
            case 5:
                keyboardInput.nextLine();
                createAccount();
                switchAccount(userAccounts.size()-1);
                break;
            case 6:
                if(userAccounts.size() > 1) {
                    switchAccount();
                } else {
                    System.out.println("You have no additional account. Press 5 to make one.");
                }
                break;
            case 7:
                renameCurrentAccount();
                break;
            case 8:
                performCloseAccount();
                break;
            case 9:
                reopenClosedAccount();
                break;
            case 10:
                performTransfer();
                break;
            case 11:
                adminMenu();
                break;
            case 12:
                System.out.println("The finanical profile");
                this.finprofile = profileCollector.collectFromUser();
                break;
            case 13:
                System.out.println("Home affordadiblity calculator section \n");
                if(finprofile == null) {
                    System.out.println("Please create a financial profile first (option 12). \n");
                } else {
                    int maxHomePrice = finprofile.calculateMaxHomePrice(); 
                    System.out.println("Based on your financial profile, you can afford a home priced up to: $" + maxHomePrice);
                }
                break;
            case 14:
                updatePassword();
                break;
            case 15:
                System.out.println("Loan Calculator section \n");
                LoanCalculator.runLoanCalculator(keyboardInput);  
                break; 
            case 16:
                System.out.println("Savings Goal Calculator section \n");
                SavingsGoalCalculator.runSavingsGoalCalculator(keyboardInput);
                break;
            case 17:
                tellerMenu();
                break;
            case 18:
                System.out.println("Exiting the app. Goodbye!");
                break;    

            default:
                System.out.println("Unknown selection.");
        }
    }

    public BankAccount selectAccount() {
        ArrayList<BankAccount> openAccounts = getOpenAccounts();

        if (openAccounts.isEmpty()) {
            System.out.println("There are no open accounts available.");
            return null;
        }

        System.out.println("Choose an account: ");
        for (int i = 0; i < openAccounts.size(); i++) {
            BankAccount account = openAccounts.get(i);
            System.out.println((i + 1) + ". " + account.getName()
                + " (ID: " + account.getID() + ")");
        }

        int selection = getUserSelection(openAccounts.size());
        return openAccounts.get(selection - 1);
    }

    private ArrayList<BankAccount> getOpenAccounts() {
        ArrayList<BankAccount> openAccounts = new ArrayList<BankAccount>();
        for (BankAccount account : userAccounts) {
            if (!account.isClosed()) {
                openAccounts.add(account);
            }
        }
        return openAccounts;
    }

    public void createAccount() {
        System.out.print("Name your new account: ");
        String accountName = promptForUniqueAccountName();
        System.out.print("Create your password: ");
        String accountPassword = readString();
        userAccounts.add(new BankAccount(accountName, accountPassword));
    }

    private String promptForUniqueAccountName() {
        while (true) {
            String accountName = readString();
            if (isBlankAccountName(accountName)) {
                System.out.println("Please input a non-blank name.");
                continue;
            }
            if (isDuplicateAccountName(accountName)) {
                System.out.println("Please input a unique account name.");
                continue;
            }
            return accountName;
        }
    }

    private String readString() {
        try {
            return keyboardInput.nextLine();
        } catch (Exception e) {
            System.out.println("Please input a proper string.");
            return "";
        }
    }

    private boolean isBlankAccountName(String accountName) {
        return accountName == null || accountName.isBlank();
    }

    private boolean isDuplicateAccountName(String accountName) {
        for (BankAccount account : userAccounts) {
            if (account.getName().equals(accountName)) {
                return true;
            }
        }
        return false;
    }

    public void switchAccount() {
        BankAccount selectedAccount = selectAccount();
        System.out.print("Input Password: ");
        keyboardInput.nextLine();
        String triedPassword = readString();

        if (selectedAccount.checkPassword(triedPassword)) {
            this.currentAccount = selectedAccount;
            System.out.println("Sign In Successful!");
        } else {
            System.out.println("Account Sign In Failed: Incorrect Password!");
        }
    }

    public void updatePassword() {
        System.out.println("Please input current password: ");
        keyboardInput.nextLine();
        String supposedPassword = readString();

        if(currentAccount.checkPassword(supposedPassword)) {
            System.out.println("Please input new password: ");
            String newPassword = readString();
            currentAccount.setPassword(newPassword);
            System.out.println("Password Successfully Updated!");
        } else {
            System.out.println("Unable to Update Password: Wrong Current Password!");
        }
    }

    public void switchAccount(int accountNum) {
        this.currentAccount = userAccounts.get(accountNum);
    }

    public BankAccount getCurrentAccount() {
        return this.currentAccount;
    }

    public void renameCurrentAccount() {
        keyboardInput.nextLine();
        String newName = readRenameName();

        if (currentAccount.isClosed()) {
            throw new IllegalStateException("Closed accounts cannot be renamed.");
        }
        if (isBlankAccountName(newName)) {
            throw new IllegalArgumentException("Please input a non-blank name.");
        }
        if (isDuplicateRename(newName)) {
            throw new IllegalArgumentException("Please input a unique account name.");
        }

        currentAccount.renameAccount(newName);
        System.out.println("Account renamed successfully.");
    }

    private String readRenameName() {
        System.out.print("Enter a new account name: ");
        return keyboardInput.nextLine();
    }

    private boolean isDuplicateRename(String newName) {
        for (BankAccount account : userAccounts) {
            if (account != currentAccount && account.getName().equals(newName)) {
                return true;
            }
        }
        return false;
    }

    public void performDeposit() {
        double depositAmount = promptForNonNegativeAmount("How much would you like to deposit: ");
        currentAccount.deposit(depositAmount);
        System.out.println("Deposit submitted! Awaiting teller approval.");
    }

    private double promptForNonNegativeAmount(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double amount = keyboardInput.nextDouble();
                if (amount < 0) {
                    System.out.println("Please enter a non-negative amount.");
                    continue;
                }
                return amount;
            } catch (InputMismatchException e) {
                System.out.println("Invalid amount. Try again.");
                keyboardInput.nextLine();
            }
        }
    }

    public void checkBalance() {
        System.out.println("Current Balance is: " + currentAccount.getBalance());
    }

    public void performWithDraw() {
        double withdrawAmount = promptForValidWithdrawAmount();
        currentAccount.withdraw(withdrawAmount);
        System.out.println("Withdrawal submitted! Awaiting teller approval.");
    }

    private double promptForValidWithdrawAmount() {
        while (true) {
            double withdrawAmount = promptForNonNegativeAmount(
                "How much would you like to withdraw: "
            );
            if (hasInsufficientFunds(withdrawAmount)) {
                System.out.println("You are unable to withdraw more than you already have.");
                continue;
            }
            return withdrawAmount;
        }
    }

    private boolean hasInsufficientFunds(double amount) {
        return currentAccount.getBalance() < amount;
    }

    public void viewTransactions() {
        if (hasNoTransactions()) {
            printNoTransactionsMessage();
            return;
        }

        displayRegularTransactionHistory();
        runTransactionHistoryMenu();
    }

    private boolean hasNoTransactions() {
        return currentAccount.getTransactions().isEmpty();
    }

    private void printNoTransactionsMessage() {
        System.out.println("Transaction history:");
        System.out.println("No transactions found.");
    }

    private void displayRegularTransactionHistory() {
        System.out.println("Transaction history:");
        for (int i = 0; i < currentAccount.getTransactions().size(); i++) {
            printTransactionByIndex(i);
        }
    }

    private void printTransactionByIndex(int index) {
        Transaction transaction = currentAccount.getTransactions().get(index);

        System.out.print((index + 1) + ". ");
        printTransactionDetails(transaction);
        System.out.println();
    }

    private void printTransactionDetails(Transaction transaction) {
        String typeStr = transactionTypeToString(transaction.getType());
        String statusStr = "[" + transaction.getStatus().name() + "]";
        System.out.print(statusStr + " " + typeStr + ": " + transaction.getAmount());
        printTransactionNote(transaction.getNote());
    }

    private String transactionTypeToString(Transaction.Type type) {
        switch(type) {
            case DEPOSIT:
                return "Deposit";
            case WITHDRAWAL:
                return "Withdrawal";
            case TRANSFER_IN:
                return "Transfer In";
            case TRANSFER_OUT:
                return "Transfer Out";
            default:
                return "Unknown";
        }
    }

    private void printTransactionNote(String note) {
        if (note != null && !note.isBlank()) {
            System.out.print(" | Note: " + note);
        }
    }

    private void runTransactionHistoryMenu() {
        while (true) {
            displayTransactionHistoryOptions();
            int selection = getUserSelection(3);

            if (selection == 1) {
                addNoteToTransaction();
            } else if (selection == 2) {
                displaySortedTransactionHistory();
            } else {
                return;
            }
        }
    }

    private void displayTransactionHistoryOptions() {
        System.out.println();
        System.out.println("Transaction History Options:");
        System.out.println("1. Add note to a transaction");
        System.out.println("2. View sorted transaction history");
        System.out.println("3. Return to main menu");
    }

    private void addNoteToTransaction() {
        int index = promptForTransactionNumber();
        String note = promptForTransactionNote();
        currentAccount.addTransactionNote(index, note);
        System.out.println("Note added successfully.");
    }

    private int promptForTransactionNumber() {
        while (true) {
            System.out.print("Enter the transaction number: ");
            try {
                int transactionNumber = keyboardInput.nextInt();
                if (isValidTransactionNumber(transactionNumber)) {
                    return transactionNumber - 1;
                }
                System.out.println("Invalid transaction number.");
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                keyboardInput.nextLine();
            }
        }
    }

    private boolean isValidTransactionNumber(int transactionNumber) {
        return transactionNumber >= 1
            && transactionNumber <= currentAccount.getTransactions().size();
    }

    private String promptForTransactionNote() {
        keyboardInput.nextLine();
        System.out.print("Enter your note: ");
        return readString();
    }

    private void displaySortedTransactionHistory() {
        System.out.println("Transaction history sorted from largest to smallest:");
        ArrayList<Integer> sortedIndices = getSortedTransactionIndices();

        for (int index : sortedIndices) {
            printSortedTransactionByIndex(index);
        }
    }

    private ArrayList<Integer> getSortedTransactionIndices() {
        ArrayList<Integer> sortedIndices = new ArrayList<Integer>();

        for (int i = 0; i < currentAccount.getTransactions().size(); i++) {
            sortedIndices.add(i);
        }

        sortedIndices.sort((a, b) -> Double.compare(
            Math.abs(currentAccount.getTransactions().get(b).getAmount()),
            Math.abs(currentAccount.getTransactions().get(a).getAmount())
        ));

        return sortedIndices;
    }

    private void printSortedTransactionByIndex(int index) {
        Transaction transaction = currentAccount.getTransactions().get(index);

        System.out.print("Original #" + (index + 1) + " - ");
        printTransactionDetails(transaction);
        System.out.println();
    }

    public void performCloseAccount() {

        if (currentAccount.isClosed()) {
            System.out.println("This account is already closed.");
            return;
        }
        currentAccount.closeAccount();
        System.out.println("Account " + currentAccount.getID() + " has been closed.");
        switchAccount();
    }

    public void reopenClosedAccount() {
        System.out.println("Choose an account to reopen:");
        BankAccount accountToReopen = selectClosedAccount();

        if (accountToReopen == null) {
            System.out.println("There are no closed accounts.");
            return;
        }

        accountToReopen.reopenAccount();
        System.out.println("Account reopened successfully.");
    }

    private BankAccount selectOpenAccount() {
        ArrayList<BankAccount> openAccounts = getOpenAccounts();
        if (openAccounts.isEmpty()) {
            return null;
        }

        for (int i = 0; i < openAccounts.size(); i++) {
            BankAccount account = openAccounts.get(i);
            System.out.println((i + 1) + ". " + account.getName());
        }

        int selection = getUserSelection(openAccounts.size());
        return openAccounts.get(selection - 1);
    }

    private BankAccount selectClosedAccount() {
        ArrayList<BankAccount> closedAccounts = getClosedAccounts();
        if (closedAccounts.isEmpty()) {
            return null;
        }

        for (int i = 0; i < closedAccounts.size(); i++) {
            BankAccount account = closedAccounts.get(i);
            System.out.println((i + 1) + ". " + account.getName());
        }

        int selection = getUserSelection(closedAccounts.size());
        return closedAccounts.get(selection - 1);
    }

    private ArrayList<BankAccount> getClosedAccounts() {
        ArrayList<BankAccount> closedAccounts = new ArrayList<>();

        for (BankAccount account : userAccounts) {
            if (account.isClosed()) {
                closedAccounts.add(account);
            }
        }

        return closedAccounts;
    }

    public void performTransfer() {
        BankAccount fromAccount = currentAccount;
        BankAccount toAccount = selectTransferTargetAccount();

        if (toAccount == null) {
            return;
        }

        double transferAmount = promptForNonNegativeAmount(
            "How much would you like to transfer: "
        );
        executeTransfer(fromAccount, toAccount, transferAmount);
    }

    private BankAccount selectTransferTargetAccount() {
        BankAccount toAccount = selectAccount();

        if (toAccount == currentAccount) {
            System.out.println("You must choose a different account.");
            return null;
        }

        return toAccount;
    }

    private void executeTransfer(BankAccount fromAccount, BankAccount toAccount, double amount) {
        try {
            fromAccount.transferTo(toAccount, amount);
            System.out.println("Transfer successful!");
        } catch (IllegalArgumentException e) {
            System.out.println("Transfer failed.");
        }
    }

    public int getAccountCount() {
        return userAccounts.size();
    }

    public void adminMenu(){
        int adminSelection = -1;
        if (!checkPassword()){
            adminSelection = ADMIN_EXIT_SELECTION; // set the selection to automatically exit the admin menu
            System.out.println("Incorrect password, exiting admin mode.");
        }
        while(adminSelection != ADMIN_EXIT_SELECTION){
            displayAdminOptions();
            adminSelection = getUserSelection(ADMIN_MAX_SELECTION);
            processAdminInput(adminSelection);
        }
    }

    public boolean checkPassword(){
        if (this.admin == null){
            System.out.println("Please input a password for the bank admin.");
            String password = readPassword();
            if (password == ""){
                return false;
            } else {
                this.admin = new BankAdmin(password);
                return true;
            }
        } else {
            System.out.print("Password: ");
            String password = readPassword();
            return admin.checkPassword(password);
        }
    }

    public String readPassword(){
        try {
            keyboardInput.nextLine(); //clear the line
            return keyboardInput.nextLine();
        } catch (Exception e) {
            System.out.println("Please try again with a proper string.");
            return "";
        }
    }

    public void displayAdminOptions(){
        System.out.println("Welcome to admin mode. Please select one of the administrative options below.");
        System.out.println("1. Collect fees");
        System.out.println("2. Make an interest payment."); 
        System.out.println("3. Freeze an account.");
        System.out.println("4. Unfreeze an account.");
        System.out.println("5. Show statistics.");
        System.out.println("6. View reported accounts");
        System.out.println("7. Exit admin mode.");
    }

    public void processAdminInput(int selection){
        switch (selection) {
            case 1:
                adminCollectFees();
                break;
            case 2:
                adminDepositInterest();
                break;
            case 3:
                adminFreezeAccount();
                break;
            case 4:
                adminUnfreezeAccount();
                break;
            case 5:
                adminShowStatistics();
                break;
            case 6:
                adminShowReported();
                break;
            case 7:
                System.out.println("Exiting admin mode.");
                break;
            default:
                System.out.println("Unknown selection.");
        }
    }

    public void adminCollectFees() {
        BankAccount collectionAccount = selectAccount();
        double feeAmount = promptForNonNegativeAmount(
            "How much would you like to charge as a fee: "
        );
        applyAdminFee(collectionAccount, feeAmount);
    }

    private void applyAdminFee(BankAccount account, double feeAmount) {
        try {
            admin.collectFees(account, feeAmount);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid amount. Fee collection cancelled.");
        }
    }

    public void adminDepositInterest() {
        BankAccount interestAccount = selectAccount();
        double depositAmount = promptForNonNegativeAmount(
            "How much would you like to deposit: "
        );
        applyAdminInterestDeposit(interestAccount, depositAmount);
    }

    private void applyAdminInterestDeposit(BankAccount account, double depositAmount) {
        try {
            admin.depositInterest(account, depositAmount);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid amount. Desposit cancelled.");
        }
    }

    private void adminFreezeAccount() {
        System.out.println("Choose an account to freeze:");
        BankAccount account = selectOpenAccount();
        if (account == null){
            System.out.println("No open accounts to freeze.");
            return;
        }
        boolean successfulFreeze = admin.freezeAccount(account);
        if (successfulFreeze){
            System.out.println("Account successfully frozen.");
        } else {
            System.out.println("Please freeze an account that isn't frozen or closed.");
        }
    }

    private void adminUnfreezeAccount() {
        System.out.println("Choose an account to unfreeze:");
        BankAccount account = selectClosedAccount();
        if (account == null){
            System.out.println("No accounts to unfreeze.");
            return;
        }
        boolean successfulUnfreeze = admin.unfreezeAccount(account);
        if (successfulUnfreeze){
            System.out.println("Account successfully unfrozen.");
        } else {
            System.out.println("Please unfreeze an account that is frozen or closed.");
        }

    }

    private void adminShowStatistics() {
        ArrayList<BankAccount> openAccounts = getOpenAccounts();
        
        System.out.println("Maximum account balance:");
        BankAccount maxAccount = admin.getMaximum(openAccounts);
        if (maxAccount == null){
            System.out.println("Error. No open accounts.");
        } else {
            System.out.println(maxAccount.getName() + " - " + maxAccount.getBalance());
        }
        
        System.out.println("Minimum account balance:");
        BankAccount minAccount = admin.getMinimum(openAccounts);
        if (minAccount == null){
            System.out.println("Error. No open accounts.");
        } else {
            System.out.println(minAccount.getName() + " - " + minAccount.getBalance());
        }

        System.out.println("Total account balance:");
        double totalBalance = admin.getTotal(openAccounts);
        System.out.println(totalBalance);
        
        System.out.println("Average account balance:");
        double average = admin.getAverage(openAccounts);
        System.out.println(average);
    }

    private void adminShowReported() {
        for(ReportedAccount account : reportedAccounts) {
            System.out.println(account.formatReport());
        }
    }

    public void tellerMenu() {
        if (!checkTellerPassword()) {
            System.out.println("Incorrect password, exiting teller mode.");
            return;
        }
        int selection = -1;
        while (selection != TELLER_EXIT_SELECTION) {
            displayTellerOptions();
            selection = getUserSelection(TELLER_MAX_SELECTION);
            processTellerInput(selection);
        }
    }

    private boolean checkTellerPassword() {
        if (this.teller == null) {
            System.out.println("Please set a password for bank teller access.");
            String password = readPassword();
            if (password.isEmpty()) {
                return false;
            }
            this.teller = new BankTeller(password);
            return true;
        } else {
            System.out.print("Teller password: ");
            String password = readPassword();
            return teller.checkPassword(password);
        }
    }

    private void displayTellerOptions() {
        System.out.println("Welcome to bank teller mode.");
        System.out.println("1. Review pending transactions");
        System.out.println("2. Exit teller mode");
    }

    private void processTellerInput(int selection) {
        switch (selection) {
            case 1:
                tellerReviewPending();
                break;
            case 2:
                System.out.println("Exiting teller mode.");
                break;
            default:
                System.out.println("Unknown selection.");
        }
    }

    private void tellerReviewPending() {
        ArrayList<BankAccount> pendingAccounts = new ArrayList<>();
        ArrayList<Transaction> pendingTransactions = new ArrayList<>();
        collectPendingTransactions(pendingAccounts, pendingTransactions);

        if (pendingTransactions.isEmpty()) {
            System.out.println("No pending transactions.");
            return;
        }

        displayPendingTransactions(pendingAccounts, pendingTransactions);

        int sel = promptForPendingTransactionSelection(pendingTransactions.size());
        Transaction selected = pendingTransactions.get(sel - 1);
        BankAccount selectedAccount = pendingAccounts.get(sel - 1);

        processTellerDecision(selectedAccount, selected);
    }

    private void collectPendingTransactions(ArrayList<BankAccount> pendingAccounts, ArrayList<Transaction> pendingTransactions) {
        for (BankAccount account : userAccounts) {
            for (Transaction transaction : account.getTransactions()) {
                if (transaction.isPending()) {
                    pendingAccounts.add(account);
                    pendingTransactions.add(transaction);
                }
            }
        }
    }

    private void displayPendingTransactions(ArrayList<BankAccount> pendingAccounts, ArrayList<Transaction> pendingTransactions) {
        System.out.println("Pending Transactions:");
        for (int i = 0; i < pendingTransactions.size(); i++) {
            printPendingTransactionEntry(i + 1, pendingAccounts.get(i), pendingTransactions.get(i));
        }
    }

    private void printPendingTransactionEntry(int number, BankAccount account, Transaction transaction) {
        String typeStr = transaction.getType() == Transaction.Type.DEPOSIT ? "Deposit" : "Withdrawal";
        String noteStr = transaction.getNote().isBlank() ? "" : " | Note: " + transaction.getNote();
        System.out.println(number + ". [" + account.getName() + " (ID: " + account.getID() + ")] " + typeStr + ": $" + transaction.getAmount() + noteStr);
    }

    private int promptForPendingTransactionSelection(int count) {
        System.out.println("Select a transaction to review: ");
        return getUserSelection(count);
    }

    private void processTellerDecision(BankAccount account, Transaction transaction) {
        System.out.println("1. Approve");
        System.out.println("2. Deny");
        System.out.println("3. Report");
        int decision = getUserSelection(3);

        if (decision == 1) {
            approvePendingTransaction(account, transaction);
        } else if (decision == 2) {
            teller.denyTransaction(transaction);
            System.out.println("Transaction denied.");
        } else {
            System.out.println("Reason for reporting: ");
            keyboardInput.nextLine();
            String reportReason = readString();
            reportedAccounts.add(new ReportedAccount(account, reportReason));
            System.out.println("Account reported to admin.");
        }
    }

    private void approvePendingTransaction(BankAccount account, Transaction transaction) {
        try {
            teller.approveTransaction(account, transaction);
            System.out.println("Transaction approved.");
        } catch (IllegalStateException e) {
            System.out.println("Could not approve transaction: " + e.getMessage());
        }
    }

    public void run() {
        int selection = -1;
        while(selection != EXIT_SELECTION) {
            displayOptions();
            selection = getUserSelection(MAX_SELECTION);
            processInput(selection);
        }
        keyboardInput.close();
    }

    public static void main(String[] args) {
        MainMenu bankApp = new MainMenu();
        bankApp.run();
    }
}