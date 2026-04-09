package main;

import java.util.Scanner;


public class FinancialProfileCollector {

    private final Scanner scanner;

    public FinancialProfileCollector(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * The entry point. Notice how it delegating work to tiny helpers.
     */
    public FinancialProfile collectFromUser() {
        // need to call this collectFromUser method in the main menu. 
        System.out.println("--- Financial Information Gathering ---");
        
        double income = promptForDouble("Enter your gross annual income: ");
        double debt = promptForDouble("Enter monthly debt (loans, credit cards): ");
        double savings = promptForDouble("Enter your total down payment amount: ");
        double rate = promptForDouble("Enter expected interest rate (e.g. 7.0): ");
        int years = (int) promptForDouble("Enter loan term in years (15 or 30): ");

        return new FinancialProfile(income, debt, savings, rate, years);
    }

    public FinancialProfile getFinancialProfile() {
        return collectFromUser();
    }

    public double promptForDouble(String prompt) {
        double value = -1;
        while (value < 0) {
            System.out.print(prompt);
            try {
                value = scanner.nextDouble();
                if (value < 0) {
                    System.out.println("Please enter a non-negative number.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        return value;
    }


}