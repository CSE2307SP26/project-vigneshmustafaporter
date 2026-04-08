package test;

import main.BankAccount;
import main.MainMenu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;
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
public void testWithdraw() {
    BankAccount testAccount = new BankAccount();
    testAccount.deposit(80);
    testAccount.withdraw(30);

    assertEquals(50, testAccount.getBalance(), 0.01);
}

@Test
public void testWithdrawOverLimit() {
    BankAccount testAccount = new BankAccount();
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
    BankAccount testAccount = new BankAccount();

    try {
        testAccount.withdraw(-20);
        fail();
    } catch (IllegalArgumentException e) {
        // pass
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
public void testClosedAccountCannotDeposit() {
    BankAccount testAccount = new BankAccount();
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

    @Test
public void testTransferOverLimit() {
    BankAccount fromAccount = new BankAccount();
    BankAccount toAccount = new BankAccount();

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
    BankAccount fromAccount = new BankAccount();
    BankAccount toAccount = new BankAccount();

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
        "\nTest Account\n".getBytes()
    ));

    MainMenu testMenu = new MainMenu();

    assertEquals(1, testMenu.getAccountCount());

    testMenu.createAdditionalAccount();

    assertEquals(2, testMenu.getAccountCount());
}

@Test
public void testAdditionalAccountBalance() {
    System.setIn(new java.io.ByteArrayInputStream(
        "\nSecond Account\n".getBytes()
    ));

    MainMenu testMenu = new MainMenu();
    testMenu.getCurrentAccount().deposit(20);
    testMenu.getCurrentAccount().withdraw(10);
    testMenu.createAdditionalAccount();
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
    String simulatedInput = "Savings\nSavings2\n";
    System.setIn(new java.io.ByteArrayInputStream(simulatedInput.getBytes()));

    MainMenu testMenu = new MainMenu();
    testMenu.createAdditionalAccount();

    assertEquals(2, testMenu.getAccountCount());
}

    @Test    
    public void testTransactionHistory() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        testAccount.withdraw(30);
        ArrayList<Double> testTransactions = new ArrayList<Double>();
        testTransactions.add(50.0);
        testTransactions.add(-30.0);
        assertEquals(testAccount.getTransactions(), testTransactions);
    }

    @Test
    public void testAccountNames() {
        BankAccount testAccount = new BankAccount("Test Name");
        assertEquals(testAccount.getName(), "Test Name");
    }
}
