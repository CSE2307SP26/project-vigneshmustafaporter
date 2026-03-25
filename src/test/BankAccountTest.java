package test;

import main.BankAccount;
import main.MainMenu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class BankAccountTest {

    @Test
    public void testDeposit() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        assertEquals(50, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidDeposit() {
        BankAccount testAccount = new BankAccount();
        try {
            testAccount.deposit(-50);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testAdditionalAccountBoolean() {
        MainMenu testMenu = new MainMenu();
        assertFalse(MainMenu.hasAdditionalAccount());
        testMenu.createAdditionalAccount();
        assertTrue(MainMenu.hasAdditionalAccount());
        assertNotNull(testMenu.getSecondAccount());
    }

    @Test
    public void testAdditionalAccountBalance() {
        MainMenu testMenu = new MainMenu();
        testMenu.getCurrentAccount().deposit(20);
        testMenu.getCurrentAccount().withdraw(10);
        testMenu.createAdditionalAccount();
        testMenu.switchAccount();
        testMenu.getCurrentAccount().deposit(50);
        testMenu.getCurrentAccount().withdraw(30);

        double accountTwoBalance = testMenu.getCurrentAccount().getBalance();
        testMenu.switchAccount();
        double accountOneBalance = testMenu.getCurrentAccount().getBalance();

        assertEquals(accountTwoBalance, 20, 0.01);
        assertEquals(accountOneBalance, 10, 0.01);

        
    public void testTransactionHistory() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        testAccount.withdraw(30);
        ArrayList<Double> testTransactions = new ArrayList<Double>();
        testTransactions.add(50.0);
        testTransactions.add(-30.0);
        assertEquals(testAccount.getTransactions(), testTransactions);
    }
}
