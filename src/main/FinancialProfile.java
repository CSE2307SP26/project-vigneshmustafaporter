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

    

}


