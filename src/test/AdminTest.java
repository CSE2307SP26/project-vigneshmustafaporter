package test;

import main.BankAccount;
import main.BankAdmin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AdminTest {
    
    @Test
    public void testFees() {
        BankAccount testAccount = new BankAccount("test", "test");
        testAccount.deposit(50);
        BankAdmin testAdmin = new BankAdmin("");
        testAdmin.collectFees(testAccount, 10);
        assertEquals(40, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidFees() {
        BankAccount testAccount = new BankAccount("test", "test");
        testAccount.deposit(50);
        BankAdmin testAdmin = new BankAdmin("");
        try {
            testAdmin.collectFees(testAccount, -10);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testInterest() {
        BankAccount testAccount = new BankAccount("test", "test");
        testAccount.deposit(50);
        BankAdmin testAdmin = new BankAdmin("");
        testAdmin.depositInterest(testAccount, 10);
        assertEquals(60, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidInterest() {
        BankAccount testAccount = new BankAccount("test", "test");
        testAccount.deposit(50);
        BankAdmin testAdmin = new BankAdmin("");
        try {
            testAdmin.depositInterest(testAccount, -10);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testTooMuchFees() {
        BankAccount testAccount = new BankAccount("test", "test");
        testAccount.deposit(50);
        BankAdmin testAdmin = new BankAdmin("");
        try {
            testAdmin.collectFees(testAccount, 60);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testGoodPassword(){
        BankAdmin testAdmin = new BankAdmin("Password");
        assertEquals(true, testAdmin.checkPassword("Password"));
    }

    public void testBadPassword(){
        BankAdmin testAdmin = new BankAdmin("StrongPassword");
        assertEquals(false, testAdmin.checkPassword("Password"));
    }

    @Test
    public void testFreezeDeposit(){
        BankAdmin testAdmin = new BankAdmin("");
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(10);
        testAdmin.freezeAccount(testAccount);
        try {
            testAccount.deposit(10);
            fail();
        } catch (IllegalStateException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testFreezeWithdraw(){
        BankAdmin testAdmin = new BankAdmin("");
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(10);
        testAdmin.freezeAccount(testAccount);
        try {
            testAccount.withdraw(10);
            fail();
        } catch (IllegalStateException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testFreezeBalance(){
        BankAdmin testAdmin = new BankAdmin("");
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(10);
        testAdmin.freezeAccount(testAccount);
        try {
            testAccount.getBalance();
            fail();
        } catch (IllegalStateException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testFreeze(){
        BankAdmin testAdmin = new BankAdmin("");
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(10);
        testAdmin.freezeAccount(testAccount);
        assertEquals(testAccount.isClosed(), true);
    }

    public void testUnfreezeDeposit(){
        BankAdmin testAdmin = new BankAdmin("");
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(10);
        testAdmin.freezeAccount(testAccount);
        testAdmin.unfreezeAccount(testAccount);
        try {
            testAccount.deposit(10);
        } catch (IllegalStateException e) {
            fail();
        }
    }

    @Test
    public void testUnfreezeWithdraw(){
        BankAdmin testAdmin = new BankAdmin("");
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(10);
        testAdmin.freezeAccount(testAccount);
        testAdmin.unfreezeAccount(testAccount);
        try {
            testAccount.withdraw(10);
        } catch (IllegalStateException e) {
            fail();
        }
    }

    @Test
    public void testUnfreezeBalance(){
        BankAdmin testAdmin = new BankAdmin("");
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(10);
        testAdmin.freezeAccount(testAccount);
        testAdmin.unfreezeAccount(testAccount);
        try {
            testAccount.getBalance();
        } catch (IllegalStateException e) {
            fail();
        }
    }

    @Test
    public void testUnfreeze(){
        BankAdmin testAdmin = new BankAdmin("");
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(10);
        testAdmin.freezeAccount(testAccount);
        testAdmin.unfreezeAccount(testAccount);
        assertEquals(testAccount.isClosed(), false);
    }

}