// ...existing code...
package main;
import java.util.Scanner;
import java.util.InputMismatchException;

public class MainMenu {

    private static final int EXIT_SELECTION = 5;
    private static final int MAX_SELECTION = 5;

    private BankAccount userAccount;
    private Scanner keyboardInput;

    public MainMenu() {
        this.userAccount = new BankAccount();
        this.keyboardInput = new Scanner(System.in);
    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App!");
        System.out.println("1. Make a deposit");
        System.out.println("2. Make a withdraw"); 
        System.out.println("3. Check your balance"); 
        System.out.println("4. Close your account");
        System.out.println("5. Exit the app");
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
                performCloseAccount();
                break;
            case 5:
                System.out.println("Exiting the app. Goodbye!");
                break;
            default:
                System.out.println("Unknown selection.");
        }
        // we want to break after user selects an option 
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
        userAccount.deposit(depositAmount);
        System.out.println("Deposit successful!");
    }

    public void checkBalance() {
       System.out.println("Current Balance is: " + userAccount.getBalance()); 
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
                else if(userAccount.getBalance() < withdrawAmount) {
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
        userAccount.withdraw(withdrawAmount);
        System.out.println("Withdrawal successful!");
    }

     public void performCloseAccount() {
        if (userAccount.isClosed()) {
            System.out.println("This account is already closed.");
            return;
        }

        userAccount.closeAccount();
        System.out.println("Your account has been closed.");
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
