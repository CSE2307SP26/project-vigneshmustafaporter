package test;

import main.BankAccount;
import main.MainMenu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class BankAccountTest {

    @Test
    public void testDeposit() {
        BankAccount testAccount = new BankAccount("test", "test");
        testAccount.deposit(50);
        assertEquals(50, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidDeposit() {
        BankAccount testAccount = new BankAccount("test", "test");
        try {
            testAccount.deposit(-50);
            fail();
        } catch (IllegalArgumentException e) {
            // do nothing, test passes
        }
    }

    @Test
    public void testWithdraw() {
        BankAccount testAccount = new BankAccount("test", "test");
        testAccount.deposit(80);
        testAccount.withdraw(30);

        assertEquals(50, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testWithdrawOverLimit() {
        BankAccount testAccount = new BankAccount("test", "test");
        testAccount.deposit(50);

        try {
            testAccount.withdraw(100);
            fail();
        } catch (IllegalArgumentException e) {
            // pass
        }

        assertEquals(50, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidWithdraw() {
        BankAccount testAccount = new BankAccount("test", "test");

        try {
            testAccount.withdraw(-20);
            fail();
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    @Test
    public void testCheckBalance() {
        System.setIn(new java.io.ByteArrayInputStream(
            "Main\npassword\n".getBytes()
        ));

        MainMenu testMenu = new MainMenu();
        testMenu.getCurrentAccount().deposit(75);

        java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
        java.io.PrintStream originalOut = System.out;
        System.setOut(new java.io.PrintStream(output));

        testMenu.checkBalance();

        System.setOut(originalOut);

        assertTrue(output.toString().contains("Current Balance is: 75.0"));
    }

    @Test
    public void testCloseAccount() {
        BankAccount testAccount = new BankAccount("test", "test");
        testAccount.closeAccount();
        assertTrue(testAccount.isClosed());

        try {
            testAccount.closeAccount();
            fail();
        } catch (IllegalStateException e) {
            // nothing
        }
    }

    @Test
    public void testClosedAccountCannotDeposit() {
        BankAccount testAccount = new BankAccount("test", "test");
        testAccount.closeAccount();

        try {
            testAccount.deposit(50);
            fail();
        } catch (IllegalStateException e) {
            // pass
        }
    }

    @Test
    public void testTransfer() {
        BankAccount fromAccount = new BankAccount("test", "test");
        BankAccount toAccount = new BankAccount("test", "test");

        fromAccount.deposit(100);
        fromAccount.transferTo(toAccount, 40);

        assertEquals(60, fromAccount.getBalance(), 0.01);
        assertEquals(40, toAccount.getBalance(), 0.01);

        try {
            fromAccount.transferTo(toAccount, 100);
            fail();
        } catch (IllegalArgumentException e) {
            // nothing
        }
    }

    @Test
    public void testTransferOverLimit() {
        BankAccount fromAccount = new BankAccount("test", "test");
        BankAccount toAccount = new BankAccount("test", "test");

        fromAccount.deposit(40);

        try {
            fromAccount.transferTo(toAccount, 100);
            fail();
        } catch (IllegalArgumentException e) {
            // pass
        }

        assertEquals(40, fromAccount.getBalance(), 0.01);
        assertEquals(0, toAccount.getBalance(), 0.01);
    }

    @Test
    public void testNegativeTransfer() {
        BankAccount fromAccount = new BankAccount("test", "test");
        BankAccount toAccount = new BankAccount("test", "test");

        fromAccount.deposit(100);

        try {
            fromAccount.transferTo(toAccount, -10);
            fail();
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    @Test
    public void testAdditionalAccountBoolean() {
        System.setIn(new java.io.ByteArrayInputStream(
            "Main\npassword\nTest Account\npassword2\n".getBytes()
        ));

        MainMenu testMenu = new MainMenu();

        assertEquals(1, testMenu.getAccountCount());

        testMenu.createAccount();

        assertEquals(2, testMenu.getAccountCount());
    }

    @Test
    public void testAdditionalAccountBalance() {
        System.setIn(new java.io.ByteArrayInputStream(
            "Main\npassword\nSecond Account\npassword2\n".getBytes()
        ));

        MainMenu testMenu = new MainMenu();
        testMenu.getCurrentAccount().deposit(20);
        testMenu.getCurrentAccount().withdraw(10);

        testMenu.createAccount();
        testMenu.switchAccount(1);
        testMenu.getCurrentAccount().deposit(50);
        testMenu.getCurrentAccount().withdraw(30);

        double accountTwoBalance = testMenu.getCurrentAccount().getBalance();

        testMenu.switchAccount(0);
        double accountOneBalance = testMenu.getCurrentAccount().getBalance();

        assertEquals(20, accountTwoBalance, 0.01);
        assertEquals(10, accountOneBalance, 0.01);
    }

    @Test
    public void testDuplicateAccountName() {
        System.setIn(new java.io.ByteArrayInputStream(
            "Savings\npassword\nSavings\nSavings2\npassword2\n".getBytes()
        ));

        MainMenu testMenu = new MainMenu();
        testMenu.createAccount();

        assertEquals(2, testMenu.getAccountCount());
    }

    @Test
    public void testRenameAccount() {
        BankAccount testAccount = new BankAccount("Savings", "test");

        testAccount.renameAccount("Travel Fund");

        assertEquals("Travel Fund", testAccount.getName());
    }

    @Test
    public void testRenameBlankName() {
        BankAccount testAccount = new BankAccount("Savings", "test");

        try {
            testAccount.renameAccount("");
            fail();
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    @Test
    public void testRenameDuplicateName() {
        System.setIn(new java.io.ByteArrayInputStream(
            "Main\npassword\nSavings\npassword2\n\nMain\n".getBytes()
        ));

        MainMenu testMenu = new MainMenu();
        testMenu.createAccount();
        testMenu.switchAccount(1);

        try {
            testMenu.renameCurrentAccount();
            fail();
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    @Test
    public void testReopenAccount() {
        BankAccount testAccount = new BankAccount("test", "test");

        testAccount.closeAccount();
        assertTrue(testAccount.isClosed());

        testAccount.reopenAccount();

        assertFalse(testAccount.isClosed());
    }

    @Test
    public void testReopenAlreadyOpenAccount() {
        BankAccount testAccount = new BankAccount("test", "test");

        try {
            testAccount.reopenAccount();
            fail();
        } catch (IllegalStateException e) {
            // pass
        }
    }

    @Test
    public void testTransactionHistory() {
        BankAccount testAccount = new BankAccount("test", "test");
        testAccount.deposit(50);
        testAccount.withdraw(30);
        ArrayList<Double> testTransactions = new ArrayList<Double>();
        testTransactions.add(50.0);
        testTransactions.add(-30.0);
        assertEquals(testAccount.getTransactions(), testTransactions);
    }

    @Test
    public void testAddTransactionNote() {
        BankAccount testAccount = new BankAccount("test", "test");
        testAccount.deposit(100);
        testAccount.withdraw(40);

        testAccount.addTransactionNote(1, "groceries");

        assertEquals("groceries", testAccount.getTransactionNote(1));
    }

    @Test
    public void testAddTransactionNoteInvalidIndex() {
        BankAccount testAccount = new BankAccount("test", "test");
        testAccount.deposit(100);

        try {
            testAccount.addTransactionNote(5, "invalid");
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testSortedTransactionHistory() {
        System.setIn(new java.io.ByteArrayInputStream(
            "Main\npassword\n2\n3\n".getBytes()
        ));

        MainMenu testMenu = new MainMenu();
        testMenu.getCurrentAccount().deposit(50);
        testMenu.getCurrentAccount().deposit(200);
        testMenu.getCurrentAccount().withdraw(100);
        java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
        java.io.PrintStream originalOut = System.out;
        System.setOut(new java.io.PrintStream(output));

        testMenu.viewTransactions();
        System.setOut(originalOut);
        String result = output.toString();
        String sortedPart = result.substring(
            result.indexOf("Transaction history sorted from largest to smallest:")
        );

        assertTrue(sortedPart.indexOf("200.0") < sortedPart.indexOf("100.0"));
        assertTrue(sortedPart.indexOf("100.0") < sortedPart.indexOf("50.0"));
    }

    @Test
    public void testSortedTransactionHistoryEmpty() {
        System.setIn(new java.io.ByteArrayInputStream(
            "Main\npassword\n".getBytes()
        ));

        MainMenu testMenu = new MainMenu();
        java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
        java.io.PrintStream originalOut = System.out;
        System.setOut(new java.io.PrintStream(output));

        testMenu.viewTransactions();
        System.setOut(originalOut);
        assertTrue(output.toString().contains("No transactions found."));
    }

    @Test
    public void testAccountNames() {
        BankAccount testAccount = new BankAccount("Test Name", "test");
        assertEquals(testAccount.getName(), "Test Name");
    }
}