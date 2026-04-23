package main;


import java.util.InputMismatchException;
import java.util.Scanner;


public class LoanCalculator {
   public static double calculateMonthlyPayment(double principal, double annualInterestRate, int termInYears) {
       if (principal <= 0 || annualInterestRate < 0 || termInYears <= 0) {
           throw new IllegalArgumentException("Invalid loan parameters");
       }
       double monthlyRate = annualInterestRate / 100 / 12;
       int totalMonths = termInYears * 12;
       return principal * (monthlyRate * Math.pow(1 + monthlyRate, totalMonths)) / (Math.pow(1 + monthlyRate, totalMonths) - 1);
   }


    // New method: Calculate total amount paid over the loan term
   public static double calculateTotalPayment(double principal, double annualInterestRate, int termInYears) {
       double monthlyPayment = calculateMonthlyPayment(principal, annualInterestRate, termInYears);
       return monthlyPayment * termInYears * 12;
   }


   // New method: Calculate total interest paid over the loan term
   public static double calculateTotalInterest(double principal, double annualInterestRate, int termInYears) {
       double totalPayment = calculateTotalPayment(principal, annualInterestRate, termInYears);
       return totalPayment - principal;
   }


   // New method: Validate loan parameters (returns true if valid)
   public static boolean validateLoanParameters(double principal, double annualInterestRate, int termInYears) {
       return principal > 0 && annualInterestRate >= 0 && termInYears > 0;
   }


   public static void runLoanCalculator(Scanner scanner) {
       try {
           System.out.print("Enter principal amount: ");
           double principal = scanner.nextDouble();
           System.out.print("Enter annual interest rate (%): ");
           double rate = scanner.nextDouble();
           System.out.print("Enter term in years: ");
           int years = scanner.nextInt();
           if (validateLoanParameters(principal, rate, years)) {
               double monthlyPayment = calculateMonthlyPayment(principal, rate, years);
               double totalPayment = calculateTotalPayment(principal, rate, years);
               double totalInterest = calculateTotalInterest(principal, rate, years);
               System.out.printf("Monthly Payment: $%.2f%n", monthlyPayment);
               System.out.printf("Total Payment: $%.2f%n", totalPayment);
               System.out.printf("Total Interest: $%.2f%n", totalInterest);
           } else {
               System.out.println("Invalid input: Principal must be positive, rate non-negative, and years positive. Please try again.");
           }
       } catch (InputMismatchException e) {
           System.out.println("Invalid input: Please enter numeric values only. Please try again.");
           scanner.nextLine(); // Clear the invalid input
       } catch (IllegalArgumentException e) {
           System.out.println("Error in calculation: " + e.getMessage());
       }
   }


}


