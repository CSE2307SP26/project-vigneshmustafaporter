// ...existing code...
package main;
import java.util.Scanner;
import java.util.InputMismatchException;

public class MainMenu {

    private static final int EXIT_SELECTION = 7;
    private static final int MAX_SELECTION = 7;

    private BankAccount userAccount1;
    private BankAccount userAccount2;
    private BankAccount currentAccount;
    private Scanner keyboardInput;

    public MainMenu() {
        this.userAccount1 = new BankAccount();
        this.currentAccount = this.userAccount1;
    
    private BankOperations operations; 
    private Scanner keyboardInput;

    public MainMenu() {
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
        System.out.println("7. Exit the app");
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
                createAdditionalAccount();
                switchAccount();
                break;
            case 6:
                switchAccount();
                break;
            case 7:
                System.out.println("Exiting the app. Goodbye!");
                break;
            default:
                System.out.println("Unknown selection.");
        }
        // we want to break after user selects an option 
    }

    public void createAdditionalAccount() {
        this.userAccount2 = new BankAccount();
    }

    public void switchAccount() {
        this.currentAccount = (this.currentAccount == this.userAccount1) ? this.userAccount2 : this.userAccount1;
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
