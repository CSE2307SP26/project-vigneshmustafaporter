package test;

import main.BankAccount;
import main.MainMenu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

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
    public void testCloseAccount() {
        BankAccount testAccount = new BankAccount();
        testAccount.closeAccount();
        assertTrue(testAccount.isClosed());

        try {
            testAccount.closeAccount();
            fail();
        } catch (IllegalStateException e) {
          //nothing
        }
    }

    @Test
    public void testTransfer() {
        BankAccount fromAccount = new BankAccount();
        BankAccount toAccount = new BankAccount();

        fromAccount.deposit(100);
        fromAccount.transferTo(toAccount, 40);

        assertEquals(60, fromAccount.getBalance(), 0.01);
        assertEquals(40, toAccount.getBalance(), 0.01);

        try {
            fromAccount.transferTo(toAccount, 100);
            fail();
        } catch (IllegalArgumentException e) {
          //nothing
        }
    }
    
    public void testAdditionalAccountBoolean() {
        MainMenu testMenu = new MainMenu();
        assertFalse(testMenu.hasAdditionalAccount());
        testMenu.createAdditionalAccount();
        assertTrue(testMenu.hasAdditionalAccount());
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
    }

        
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
