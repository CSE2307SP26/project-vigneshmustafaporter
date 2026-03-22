package main;

import java.util.InputMismatchException;
import java.util.Scanner;


public class BankOperations {
    private Scanner keyboardInput;
    private BankAccount userAccount;

    public BankOperations() {
        this.keyboardInput = new Scanner(System.in);
        this.userAccount = new BankAccount();
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
}
