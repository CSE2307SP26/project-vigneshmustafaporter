package main;

public class AffordabilityCalculator {
    private static final double FRONT_END_RATIO = 0.28;
    private static final double BACK_END_RATIO = 0.36;
    private static final int MONTHS_IN_YEAR = 12;
    // enums to esnure that we have no magic numbers in the code. 
    

    public static int calculateMaxHomePrice(FinancialProfile profile) {
        double monthlyLimit = calculateMaxMonthlyPayment(profile);
        double loanAmount = calculateLoanPrincipal(monthlyLimit, profile);
        return (int) (loanAmount + profile.getDownPayment());
        // cast it to an int, so it is a clean number
        // dependency injection for the financial profile. 
    }

    private static double calculateMaxMonthlyPayment(FinancialProfile profile) {
        double monthlyGross = profile.getAnnualIncome() / MONTHS_IN_YEAR;
        
        // we need a housingLimit
        double housingLimit = monthlyGross * FRONT_END_RATIO;
        
        // debtLimit is the backend ratio times the monthly gorss income. 
        double debtLimit = (monthlyGross * BACK_END_RATIO) - profile.getMonthlyDebt();
        
        return Math.min(housingLimit, debtLimit);
        // return minimum to satsify both. 
    }

    private static double calculateLoanPrincipal(double monthlyPayment, FinancialProfile profile) {
        double INTEREST_RATE = (profile.getInterestRate() / 100) / MONTHS_IN_YEAR; // Monthly interest
        int TOTAL_PAYMENTS = profile.getTermYears() * MONTHS_IN_YEAR;               // Total payments
        
        // Mortgage Present Value Formula:
        // L = M * ((1 + r)^n - 1) / (r * (1 + r)^n)
        double power = Math.pow(1 + INTEREST_RATE, TOTAL_PAYMENTS);
        return monthlyPayment * (power - 1) / (INTEREST_RATE * power);
    }
}
