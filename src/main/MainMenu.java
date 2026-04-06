// ...existing code...
package main;
import java.util.Scanner;
import java.util.InputMismatchException;

public class MainMenu {

    private static final int EXIT_SELECTION = 10;
    private static final int MAX_SELECTION = 10;
    private static final int ADMIN_EXIT_SELECTION = 3;
    private static final int ADMIN_MAX_SELECTION = 3;
    private static boolean HAS_ADDITIONAL = false;

    private BankAccount userAccount1;
    private BankAccount userAccount2;
    private BankAccount currentAccount;
    private Scanner keyboardInput;
    private BankAdmin admin;

    public MainMenu() {
        this.userAccount1 = new BankAccount();
        this.currentAccount = this.userAccount1;
        this.keyboardInput = new Scanner(System.in);
        this.admin = new BankAdmin();
    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App! You are currently using account " + this.currentAccount.getID());
        System.out.println("1. Make a deposit");
        System.out.println("2. Make a withdraw"); 
        System.out.println("3. Check your balance"); 
        System.out.println("4. Check transaction history"); 
        System.out.println("5. Create additional account"); 
        System.out.println("6. Switch account");
        System.out.println("7. Close your account");
        System.out.println("8. Transfer money");
        System.out.println("9. Enter admin controls");
        System.out.println("10. Exit the app");
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
                if (!HAS_ADDITIONAL) {
                    createAdditionalAccount();
                    switchAccount();
                } else {
                    System.out.println("You already have an additional account. Press 6 to switch.");
                }
                break;
                
            case 6:
                if(HAS_ADDITIONAL) {
                    switchAccount();
                } else {
                    System.out.println("You have no additional account. Press 5 to make one.");
                }
                break;
            case 7:
                performCloseAccount();
                break;
            case 8:
                performTransfer();
                break;
            case 9:
                adminMenu();
                break;
            case 10:
                System.out.println("Exiting the app. Goodbye!");
                break;    
            default:
                System.out.println("Unknown selection.");
        }
        // we want to break after user selects an option 
    }

    public BankAccount selectAccount(){
        int max = 1;
        System.out.println("Choose an account");
        System.out.println("1. " + userAccount1.getID());
        if (HAS_ADDITIONAL){
            max++;
            System.out.println("2. " + userAccount2.getID());
        }
        int selection = getUserSelection(max);
        if (selection == 1){
            return userAccount1;
        } else {
            return userAccount2;
        }


    }

    public void createAdditionalAccount() {
        this.userAccount2 = new BankAccount();
        HAS_ADDITIONAL = true;
    }

    public void switchAccount() {
        this.currentAccount = (this.currentAccount == this.userAccount1) ? this.userAccount2 : this.userAccount1;
    }

    public BankAccount getCurrentAccount() {
        return this.currentAccount;
    }

    public boolean hasAdditionalAccount() {
        return HAS_ADDITIONAL;
    }

    public BankAccount getSecondAccount() {
        return this.userAccount2;
    }

    public void performDeposit() {
        double depositAmount = -1;
        while(depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            try {
                depositAmount = keyboardInput.nextDouble();
                if (depositAmount < 0) {
                    System.out.println("Please enter a non-negative amount.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid amount. Try again.");
                keyboardInput.nextLine();
            }
        }
        currentAccount.deposit(depositAmount);
        System.out.println("Deposit successful!");
    }

    public void checkBalance() {
        System.out.println("Current Balance is: " + currentAccount.getBalance()); 
    }

    public void performWithDraw() {
        double withdrawAmount = 0; 
        boolean isValid = false; 
        while(!isValid) {
            System.out.print("How much would you like to withdraw: ");
            try {
                withdrawAmount = keyboardInput.nextDouble();
                if(withdrawAmount < 0) {
                    System.out.println("You have entered a negative number, please try again.");
                }
                else if(currentAccount.getBalance() < withdrawAmount) {
                    System.out.println("You are unable to withdraw more than you already have.");
                }
                else {
                    isValid = true; 
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid amount. Try again.");
                keyboardInput.nextLine();
            }
        }
        currentAccount.withdraw(withdrawAmount);
        System.out.println("Withdrawal successful!");
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

    public void performTransfer() {
        BankAccount fromAccount = currentAccount;
        int toChoice = -1;

        while (toChoice != 1 && toChoice != 2) {
            System.out.print("Transfer TO account (1 or 2): ");
            try {
                toChoice = keyboardInput.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter 1 or 2.");
                keyboardInput.nextLine();
            }
        }

        BankAccount toAccount = (toChoice == 1) ? userAccount1 : userAccount2;

        if (toAccount == null) {
            System.out.println("That account does not exist.");
            return;
        }

        if (toAccount == currentAccount) {
            System.out.println("You must choose a different account.");
            return;
        }

        double transferAmount = -1;
        while(transferAmount < 0) {
            System.out.print("How much would you like to transfer: ");
            try {
                transferAmount = keyboardInput.nextDouble();
                if (transferAmount < 0) {
                    System.out.println("Please enter a non-negative amount.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid amount. Try again.");
                keyboardInput.nextLine();
            }
        }

        try {
            fromAccount.transferTo(toAccount, transferAmount);
            System.out.println("Transfer successful!");
        } catch (IllegalArgumentException e) {
            System.out.println("Transfer failed.");
        }

    }

    public void adminMenu(){
        int adminSelection = -1;
        while(adminSelection != ADMIN_EXIT_SELECTION){
            displayAdminOptions();
            adminSelection = getUserSelection(ADMIN_MAX_SELECTION);
            processAdminInput(adminSelection);
        }
    }

    public void displayAdminOptions(){
        System.out.println("Welcome to admin mode. Please select one of the administrative options below.");
        System.out.println("1. Collect fees");
        System.out.println("2. Make an interest payment."); 
        System.out.println("3. Exit admin mode.");
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
                System.out.println("Exiting admin mode.");
                break;
            default:
                System.out.println("Unknown selection.");
        }
    }



    public void adminCollectFees(){
        BankAccount collectionAccount;
        collectionAccount = selectAccount();
        double feeAmount = -1;
        while(feeAmount < 0) {
            System.out.print("How much would you like to charge as a fee: ");
            try {
                feeAmount = keyboardInput.nextDouble();
                if (feeAmount < 0) {
                    System.out.println("Please enter a non-negative amount.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid amount. Try again.");
                keyboardInput.nextLine();
            }
        }
        try {
            admin.collectFees(collectionAccount, feeAmount);
        } catch(IllegalArgumentException e) {
            System.out.println("Invalid amount. Fee collection cancelled.");
        }
        
    }

    public void adminDepositInterest(){
        BankAccount interestAccount;
        interestAccount = selectAccount();
        double depositAmount = -1;
        while(depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            try {
                depositAmount = keyboardInput.nextDouble();
                if (depositAmount < 0) {
                    System.out.println("Please enter a non-negative amount.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid amount. Try again.");
                keyboardInput.nextLine();
            }
        }
        try {
            admin.depositInterest(interestAccount, depositAmount);
        } catch(IllegalArgumentException e) {
            System.out.println("Invalid amount. Desposit cancelled.");
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