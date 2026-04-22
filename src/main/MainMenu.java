package main;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;

public class MainMenu {

    private static final int EXIT_SELECTION = 15;
    private static final int MAX_SELECTION = 15;
    private static final int ADMIN_EXIT_SELECTION = 6;
    private static final int ADMIN_MAX_SELECTION = 6;
    private ArrayList<BankAccount> userAccounts;
    private BankAccount currentAccount;
    private Scanner keyboardInput;
    private BankAdmin admin;
    private FinancialProfileCollector profileCollector;
    private FinancialProfile finprofile;
   
    public MainMenu() {
        this.keyboardInput = new Scanner(System.in);
        this.userAccounts = new ArrayList<BankAccount>();
        System.out.println("Account Setup:");
        createAccount();
        this.currentAccount = userAccounts.get(0);
        this.admin = null;
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
        System.out.println("15. Exit the app");
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
                    int maxHomePrice = AffordabilityCalculator.calculateMaxHomePrice(finprofile);
                    System.out.println("Based on your financial profile, you can afford a home priced up to: $" + maxHomePrice);
                }
                break;
            case 14:
                updatePassword();
                break;
            case 15:
                System.out.println("Exiting the app. Goodbye!");
                break;    
            default:
                System.out.println("Unknown selection.");
        }
        // we want to break after user selects an option 
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
        while(getOpenAccounts().size() < 1){
            System.out.println("You have no open accounts. Please create a new account.");
            createAccount();
            switchAccount(userAccounts.size()-1); //automatically switch to new account
            return;
        }
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
        double depositAmount = promptForNonNegativeAmount(
            "How much would you like to deposit: "
        );
        currentAccount.deposit(depositAmount);
        System.out.println("Deposit successful!");
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
        System.out.println("Withdrawal successful!");
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
        System.out.println("Transaction history:");
        for(double value : currentAccount.getTransactions()) {
            if(value >= 0) {
                System.out.println("Deposit: " + value);
            } else {
                System.out.println("Withdraw: " + value * -1);
            }
        }
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
        System.out.println("6. Exit admin mode.");
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