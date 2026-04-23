package main;


import java.util.Scanner;
import java.util.InputMismatchException;


public class SavingsGoalCalculator {
   public static int calculateMonthsToGoal(double currentSavings, double monthlyContribution, double annualInterestRate, double targetAmount) {
       if (currentSavings < 0 || monthlyContribution <= 0 || annualInterestRate < 0 || targetAmount <= currentSavings) {
           throw new IllegalArgumentException("Invalid savings parameters");
       }
       double monthlyRate = annualInterestRate / 100 / 12;
       int months = 0;
       double balance = currentSavings;
       while (balance < targetAmount) {
           balance += monthlyContribution;
           balance *= (1 + monthlyRate);
           months++;
           if (months > 1200) { // it is typically determined 100 years is enough
               throw new IllegalArgumentException("Goal unreachable within reasonable time");
           }
       }
       return months;
   }


   public static double calculateFutureValue(double currentSavings, double monthlyContribution, double annualInterestRate, int months) {
       if (currentSavings < 0 || monthlyContribution < 0 || annualInterestRate < 0 || months < 0) {
           throw new IllegalArgumentException("Invalid parameters");
       }
       double monthlyRate = annualInterestRate / 100 / 12;
       double balance = currentSavings;
       for (int i = 0; i < months; i++) {
           balance += monthlyContribution;
           balance *= (1 + monthlyRate);
       }
       return balance;
   }


   public static void runSavingsGoalCalculator(Scanner scanner) {
       // put this into a try catch bloc that catches mismatched inputs- people need to stop putting in neg numbers
       try {
           System.out.print("Enter current savings: ");
           double current = scanner.nextDouble();
           System.out.print("Enter monthly contribution: ");
           double contribution = scanner.nextDouble();
           System.out.print("Enter annual interest rate (%): ");
           double rate = scanner.nextDouble();
           System.out.print("Enter target savings amount: ");
           double target = scanner.nextDouble();
           if (current >= 0 && contribution > 0 && rate >= 0 && target > current) {
               int months = calculateMonthsToGoal(current, contribution, rate, target);
               double futureValue = calculateFutureValue(current, contribution, rate, months);
               System.out.printf("It will take %d months to reach your goal.%n", months);
               System.out.printf("Projected savings at goal: $%.2f%n", futureValue);
           } else {
               System.out.println("Invalid input: Ensure current >= 0, contribution > 0, rate >= 0, and target > current. Please try again.");
           }
       } catch (InputMismatchException e) {
           System.out.println("Invalid input: Please enter numeric values only. Please try again.");
           scanner.nextLine();
       } catch (IllegalArgumentException e) {
           System.out.println("Error in calculation: " + e.getMessage());
       }
   }
}
