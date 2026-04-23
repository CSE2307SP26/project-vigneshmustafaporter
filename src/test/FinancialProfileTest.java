package test;

import main.FinancialProfile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class FinancialProfileTest {

    @Test
    public void testAnnualIncome() {
        FinancialProfile testProfile = new FinancialProfile(85000, 500, 20000, 6.75, 30);
        assertEquals(85000, testProfile.getAnnualIncome(), 0.01);
    }

    @Test
    public void testMonthlyDebt() {
        FinancialProfile testProfile = new FinancialProfile(85000, 500, 20000, 6.75, 30);
        assertEquals(500, testProfile.getMonthlyDebt(), 0.01);
    }

    @Test
    public void testDownPayment() {
        FinancialProfile testProfile = new FinancialProfile(85000, 500, 20000, 6.75, 30);
        assertEquals(20000, testProfile.getDownPayment(), 0.01);
    }

    @Test
    public void testInterestRate() {
        FinancialProfile testProfile = new FinancialProfile(85000, 500, 20000, 6.75, 30);
        assertEquals(6.75, testProfile.getInterestRate(), 0.01);
    }

    @Test
    public void testTermYears() {
        FinancialProfile testProfile = new FinancialProfile(85000, 500, 20000, 6.75, 30);
        assertEquals(30, testProfile.getTermYears());
    }

    @Test
    public void testZeroAnnualIncome() {
        FinancialProfile testProfile = new FinancialProfile(0, 500, 20000, 6.75, 30);
        assertEquals(0, testProfile.getAnnualIncome(), 0.01);
    }

    @Test
    public void testZeroMonthlyDebt() {
        FinancialProfile testProfile = new FinancialProfile(85000, 0, 20000, 6.75, 30);
        assertEquals(0, testProfile.getMonthlyDebt(), 0.01);
    }

    @Test
    public void testZeroDownPayment() {
        FinancialProfile testProfile = new FinancialProfile(85000, 500, 0, 6.75, 30);
        assertEquals(0, testProfile.getDownPayment(), 0.01);
    }

    @Test
    public void testZeroInterestRate() {
        FinancialProfile testProfile = new FinancialProfile(85000, 500, 20000, 0, 30);
        assertEquals(0, testProfile.getInterestRate(), 0.01);
    }

    @Test
    public void testZeroTermYears() {
        FinancialProfile testProfile = new FinancialProfile(85000, 500, 20000, 6.75, 0);
        assertEquals(0, testProfile.getTermYears());
    }
}
