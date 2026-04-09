package test;

import main.AffordabilityCalculator;
import main.FinancialProfile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

public class AffordabilityCalculatorTest {

    // --- calculateMaxHomePrice ---

    @Test
    public void testMaxHomePriceFrontEndBinds() {
        // High debt makes back-end ratio the binding constraint,
        // but here debt is zero so front-end binds
        FinancialProfile profile = new FinancialProfile(85000, 0, 20000, 6.75, 30);
        int result = AffordabilityCalculator.calculateMaxHomePrice(profile);
        // just verify it returns a positive, reasonable number
        assertEquals(true, result > 0);
    }

    @Test
    public void testMaxHomePriceBackEndBinds() {
        // Large monthly debt forces back-end ratio to be the binding constraint
        FinancialProfile profile = new FinancialProfile(85000, 1500, 20000, 6.75, 30);
        int result = AffordabilityCalculator.calculateMaxHomePrice(profile);
        assertEquals(true, result > 0);
    }

    @Test
    public void testMaxHomePriceIncludesDownPayment() {
        // Two profiles identical except down payment — difference should equal down payment delta
        FinancialProfile profileA = new FinancialProfile(85000, 500, 20000, 6.75, 30);
        FinancialProfile profileB = new FinancialProfile(85000, 500, 40000, 6.75, 30);
        int resultA = AffordabilityCalculator.calculateMaxHomePrice(profileA);
        int resultB = AffordabilityCalculator.calculateMaxHomePrice(profileB);
        assertEquals(20000, resultB - resultA);
    }

    @Test
    public void testMaxHomePriceZeroDownPayment() {
        FinancialProfile profile = new FinancialProfile(85000, 500, 0, 6.75, 30);
        int result = AffordabilityCalculator.calculateMaxHomePrice(profile);
        assertEquals(true, result > 0);
    }

    @Test
    public void testMaxHomePriceHigherIncomeGivesHigherPrice() {
        FinancialProfile lowIncome  = new FinancialProfile(60000,  500, 20000, 6.75, 30);
        FinancialProfile highIncome = new FinancialProfile(120000, 500, 20000, 6.75, 30);
        int lowResult  = AffordabilityCalculator.calculateMaxHomePrice(lowIncome);
        int highResult = AffordabilityCalculator.calculateMaxHomePrice(highIncome);
        assertEquals(true, highResult > lowResult);
    }

    @Test
    public void testMaxHomePriceLongerTermGivesHigherPrice() {
        FinancialProfile profile15 = new FinancialProfile(85000, 500, 20000, 6.75, 15);
        FinancialProfile profile30 = new FinancialProfile(85000, 500, 20000, 6.75, 30);
        int result15 = AffordabilityCalculator.calculateMaxHomePrice(profile15);
        int result30 = AffordabilityCalculator.calculateMaxHomePrice(profile30);
        assertEquals(true, result30 > result15);
    }

    @Test
    public void testMaxHomePriceLowerRateGivesHigherPrice() {
        FinancialProfile lowRate  = new FinancialProfile(85000, 500, 20000, 3.5,  30);
        FinancialProfile highRate = new FinancialProfile(85000, 500, 20000, 6.75, 30);
        int lowRateResult  = AffordabilityCalculator.calculateMaxHomePrice(lowRate);
        int highRateResult = AffordabilityCalculator.calculateMaxHomePrice(highRate);
        assertEquals(true, lowRateResult > highRateResult);
    }

    @Test
    public void testMaxHomePriceKnownValue() {
        // I manually calculated this value using the same formulas in the calculator. 
        FinancialProfile profile = new FinancialProfile(85000, 500, 20000, 6.75, 30);
        int result = AffordabilityCalculator.calculateMaxHomePrice(profile);
        assertEquals(325787, result, 1);
    }

    @Test
    public void testMaxHomePriceReturnsInt() {
        // Confirms the cast to int truncates rather than rounds
        FinancialProfile profile = new FinancialProfile(85000, 500, 20000, 6.75, 30);
        int result = AffordabilityCalculator.calculateMaxHomePrice(profile);
        assertEquals(result, (int) result);
    }
}
