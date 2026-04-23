package main;

public class FinancialProfile {

    // boilerplate code for finacial profile. 


    private final double annualIncome;
    private final double monthlyDebt;
    private final double downPayment;
    private final double interestRate;
    private final int termYears;

    public FinancialProfile(double annualIncome, double monthlyDebt, double downPayment, double interestRate, int termYears) {
        this.annualIncome = annualIncome;
        this.monthlyDebt = monthlyDebt;
        this.downPayment = downPayment;
        this.interestRate = interestRate;
        this.termYears = termYears;
    }
    // continue to use the getters. 
    public double getAnnualIncome() {
        return annualIncome;
    }

    public double getMonthlyDebt() {
        return monthlyDebt;
    }

    public double getDownPayment() {
        return downPayment;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public int getTermYears() {
        return termYears;
    }

    private static final double FRONT_END_RATIO = 0.28;
    private static final double BACK_END_RATIO = 0.36;
    private static final int MONTHS_IN_YEAR = 12;
    // enums to esnure that we have no magic numbers in the code. 
    

    public int calculateMaxHomePrice() {
        double monthlyLimit = calculateMaxMonthlyPayment();
        double loanAmount = calculateLoanPrincipal(monthlyLimit);
        return (int) (loanAmount + downPayment);
        // cast it to an int, so it is a clean number
        // dependency injection for the financial profile. 
    }

    public double calculateMaxMonthlyPayment() {
        double monthlyGross = annualIncome / MONTHS_IN_YEAR;
        
        // we need a housingLimit
        double housingLimit = monthlyGross * FRONT_END_RATIO;
        
        // debtLimit is the backend ratio times the monthly gorss income. 
        double debtLimit = (monthlyGross * BACK_END_RATIO) - monthlyDebt;
        
        return Math.min(housingLimit, debtLimit);
        // return minimum to satsify both. 
    }

    public double calculateLoanPrincipal(double monthlyPayment) {
        double INTEREST_RATE = (interestRate / 100) / MONTHS_IN_YEAR; // Monthly interest
        int TOTAL_PAYMENTS = termYears * MONTHS_IN_YEAR;               // Total payments
        
        // Mortgage Present Value Formula:
        // L = M * ((1 + r)^n - 1) / (r * (1 + r)^n)
        double power = Math.pow(1 + INTEREST_RATE, TOTAL_PAYMENTS);
        return monthlyPayment * (power - 1) / (INTEREST_RATE * power);
    }

    

}


