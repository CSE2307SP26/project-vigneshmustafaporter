// ...existing code...
package main;
import java.util.Scanner;
import java.util.InputMismatchException;

public class MainMenu {

    private static final int EXIT_SELECTION = 9;
    private static final int MAX_SELECTION = 9;
    private static boolean HAS_ADDITIONAL = false;

    private BankAccount userAccount1;
    private BankAccount userAccount2;
    private BankAccount currentAccount;
    private Scanner keyboardInput;
    private BankOperations operations; 

    public MainMenu() {
        this.userAccount1 = new BankAccount();
        this.currentAccount = this.userAccount1;
        this.operations = new BankOperations();
        this.keyboardInput = new Scanner(System.in);
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
        System.out.println("9. Exit the app");
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
                operations.performDeposit();
                break;
            case 2:
                operations.performWithDraw();
                break;
            case 3:
                operations.checkBalance();
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
                switchAccount();
                break;
            case 7:
                performCloseAccount();
                break;
            case 8:
                performTransfer();
                break;
            case 9:
                System.out.println("Exiting the app. Goodbye!");
                break;    
            default:
                System.out.println("Unknown selection.");
        }
        // we want to break after user selects an option 
    }

    public void createAdditionalAccount() {
        this.userAccount2 = new BankAccount();
        HAS_ADDITIONAL = true;
    }

    public void switchAccount() {
        this.currentAccount = (this.currentAccount == this.userAccount1) ? this.userAccount2 : this.userAccount1;
    }


    public void performDeposit() {
        BankAccount userAccount = currentAccount;
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
        userAccount.deposit(depositAmount);
        System.out.println("Deposit successful!");
    }

    public void checkBalance() {
        System.out.println("Current Balance is: " + currentAccount.getBalance()); 
    }

    public void performWithDraw() {
        BankAccount userAccount = currentAccount;
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
        BankAccount userAccount = currentAccount;

        if (userAccount.isClosed()) {
            System.out.println("This account is already closed.");
            return;
        }

        userAccount.closeAccount();
        System.out.println("Account " + currentAccount + " has been closed.");
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
