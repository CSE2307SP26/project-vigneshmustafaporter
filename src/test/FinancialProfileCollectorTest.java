package test;

import main.FinancialProfile;
import main.FinancialProfileCollector;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.util.Scanner;

public class FinancialProfileCollectorTest {

   // this is bascially to make sure that the prompting is corect. 

    @Test
    public void testPromptForDoubleValidInput() {
        Scanner scanner = new Scanner("50000\n");
        FinancialProfileCollector collector = new FinancialProfileCollector(scanner);
        assertEquals(50000, collector.promptForDouble("Enter: "), 0.01);
    }

    @Test
    public void testPromptForDoubleZeroInput() {
        Scanner scanner = new Scanner("0\n");
        FinancialProfileCollector collector = new FinancialProfileCollector(scanner);
        assertEquals(0, collector.promptForDouble("Enter: "), 0.01);
    }

    @Test
    public void testPromptForDoubleInvalidThenValid() {
        // Feeds bad input first, then a valid number — simulates the retry loop
        Scanner scanner = new Scanner("abc\n50000\n");
        FinancialProfileCollector collector = new FinancialProfileCollector(scanner);
        assertEquals(50000, collector.promptForDouble("Enter: "), 0.01);
    }

    @Test
    public void testPromptForDoubleNegativeThenValid() {
        // Feeds a negative first, then a valid number — simulates the retry loop
        Scanner scanner = new Scanner("-5\n50000\n");
        FinancialProfileCollector collector = new FinancialProfileCollector(scanner);
        assertEquals(50000, collector.promptForDouble("Enter: "), 0.01);
    }

    @Test
    public void testPromptForDoubleDecimalInput() {
        Scanner scanner = new Scanner("6.75\n");
        FinancialProfileCollector collector = new FinancialProfileCollector(scanner);
        assertEquals(6.75, collector.promptForDouble("Enter: "), 0.01);
    }

    // --- collectFromUser / getFinancialProfile ---

    @Test
    public void testCollectFromUserIncome() {
        Scanner scanner = new Scanner("85000\n500\n20000\n6.75\n30\n");
        FinancialProfileCollector collector = new FinancialProfileCollector(scanner);
        FinancialProfile profile = collector.collectFromUser();
        assertEquals(85000, profile.getAnnualIncome(), 0.01);
    }

    @Test
    public void testCollectFromUserMonthlyDebt() {
        Scanner scanner = new Scanner("85000\n500\n20000\n6.75\n30\n");
        FinancialProfileCollector collector = new FinancialProfileCollector(scanner);
        FinancialProfile profile = collector.collectFromUser();
        assertEquals(500, profile.getMonthlyDebt(), 0.01);
    }

    @Test
    public void testCollectFromUserDownPayment() {
        Scanner scanner = new Scanner("85000\n500\n20000\n6.75\n30\n");
        FinancialProfileCollector collector = new FinancialProfileCollector(scanner);
        FinancialProfile profile = collector.collectFromUser();
        assertEquals(20000, profile.getDownPayment(), 0.01);
    }

    @Test
    public void testCollectFromUserInterestRate() {
        Scanner scanner = new Scanner("85000\n500\n20000\n6.75\n30\n");
        FinancialProfileCollector collector = new FinancialProfileCollector(scanner);
        FinancialProfile profile = collector.collectFromUser();
        assertEquals(6.75, profile.getInterestRate(), 0.01);
    }

    @Test
    public void testCollectFromUserTermYears() {
        Scanner scanner = new Scanner("85000\n500\n20000\n6.75\n30\n");
        FinancialProfileCollector collector = new FinancialProfileCollector(scanner);
        FinancialProfile profile = collector.collectFromUser();
        assertEquals(30, profile.getTermYears());
    }

    @Test
    public void testGetFinancialProfileMatchesCollectFromUser() {
        Scanner scanner = new Scanner("85000\n500\n20000\n6.75\n30\n");
        FinancialProfileCollector collector = new FinancialProfileCollector(scanner);
        FinancialProfile profile = collector.getFinancialProfile();
        assertEquals(85000, profile.getAnnualIncome(), 0.01);
    }
}