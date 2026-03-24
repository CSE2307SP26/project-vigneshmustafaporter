package test;

import main.BankAccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
