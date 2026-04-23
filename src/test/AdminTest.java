package test;

import main.BankAccount;
import main.BankAdmin;
import main.BankTeller;
import main.MainMenu;
import main.ReportedAccount;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

public class AdminTest {
    
	@Test
	public void testFees() {
	    BankAccount account = new BankAccount("test", "test");
	    BankAdmin admin = new BankAdmin("");
	    BankTeller teller = new BankTeller("teller");

	    account.deposit(50);
	    teller.approveTransaction(account, account.getTransactions().get(0));

	    admin.collectFees(account, 10);

	    teller.approveTransaction(account, account.getTransactions().get(1));

	    assertEquals(40, account.getBalance(), 0.01);
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
        BankAccount account = new BankAccount("test", "test");
        BankAdmin admin = new BankAdmin("");
        BankTeller teller = new BankTeller("teller");

        account.deposit(50);
        teller.approveTransaction(account, account.getTransactions().get(0));

        admin.depositInterest(account, 10);

        // approve interest deposit
        teller.approveTransaction(account, account.getTransactions().get(1));

        assertEquals(60, account.getBalance(), 0.01);
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
        BankAccount testAccount = new BankAccount("test", "test");
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
        BankAccount testAccount = new BankAccount("test", "test");
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
        BankAccount testAccount = new BankAccount("test", "test");
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
        BankAccount testAccount = new BankAccount("test", "test");
        testAccount.deposit(10);
        testAdmin.freezeAccount(testAccount);
        assertEquals(testAccount.isClosed(), true);
    }

    public void testUnfreezeDeposit(){
        BankAdmin testAdmin = new BankAdmin("");
        BankAccount testAccount = new BankAccount("test", "test");
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
        BankAdmin admin = new BankAdmin("");
        BankTeller teller = new BankTeller("teller");

        BankAccount account = new BankAccount("test", "test");

        account.deposit(10);
        teller.approveTransaction(account, account.getTransactions().get(0));

        admin.freezeAccount(account);
        admin.unfreezeAccount(account);

        try {
            account.withdraw(10);
            teller.approveTransaction(account, account.getTransactions().get(1));
        } catch (IllegalStateException e) {
            fail();
        }
    }

    @Test
    public void testUnfreezeBalance(){
        BankAdmin testAdmin = new BankAdmin("");
        BankAccount testAccount = new BankAccount("test", "test");
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
        BankAccount testAccount = new BankAccount("test", "test");
        testAccount.deposit(10);
        testAdmin.freezeAccount(testAccount);
        testAdmin.unfreezeAccount(testAccount);
        assertEquals(testAccount.isClosed(), false);
    }

    @Test
    public void testMax(){
        BankAdmin testAdmin = new BankAdmin("");
        BankTeller teller = new BankTeller("teller");

        BankAccount testAccount1 = new BankAccount("1", "");
        BankAccount testAccount2 = new BankAccount("2", "");

        testAccount1.deposit(10);
        teller.approveTransaction(testAccount1, testAccount1.getTransactions().get(0));

        testAccount2.deposit(20);
        teller.approveTransaction(testAccount2, testAccount2.getTransactions().get(0));

        ArrayList<BankAccount> testAccounts = new ArrayList<>();
        testAccounts.add(testAccount1);
        testAccounts.add(testAccount2);

        BankAccount maxAccount = testAdmin.getMaximum(testAccounts);
        assertEquals(testAccount2, maxAccount);
    }

    @Test
    public void testEmptyMax(){
        BankAdmin testAdmin = new BankAdmin("");
        ArrayList<BankAccount> testAccounts = new ArrayList<BankAccount>();
        BankAccount maxAccount = testAdmin.getMaximum(testAccounts);
        assertEquals(maxAccount, null);
    }

    @Test
    public void testMin(){
        BankAdmin testAdmin = new BankAdmin("");
        BankAccount testAccount1 = new BankAccount("1", "");
        BankAccount testAccount2 = new BankAccount("2", "");
        testAccount1.deposit(10);
        testAccount2.deposit(20);
        ArrayList<BankAccount> testAccounts = new ArrayList<BankAccount>();
        testAccounts.add(testAccount1);
        testAccounts.add(testAccount2);
        BankAccount minAccount = testAdmin.getMinimum(testAccounts);
        assertEquals(minAccount, testAccount1);
    }

    @Test
    public void testEmptyMin(){
        BankAdmin testAdmin = new BankAdmin("");
        ArrayList<BankAccount> testAccounts = new ArrayList<BankAccount>();
        BankAccount maxAccount = testAdmin.getMinimum(testAccounts);
        assertEquals(maxAccount, null);
    }

    @Test
    public void testTotal(){
        BankAdmin testAdmin = new BankAdmin("");
        BankTeller teller = new BankTeller("teller");

        BankAccount a1 = new BankAccount("1", "");
        BankAccount a2 = new BankAccount("2", "");

        a1.deposit(10);
        teller.approveTransaction(a1, a1.getTransactions().get(0));

        a2.deposit(20);
        teller.approveTransaction(a2, a2.getTransactions().get(0));

        ArrayList<BankAccount> accounts = new ArrayList<>();
        accounts.add(a1);
        accounts.add(a2);

        double total = testAdmin.getTotal(accounts);
        assertEquals(30, total);
    }

    @Test
    public void testEmptyTotal(){
        BankAdmin testAdmin = new BankAdmin("");
        ArrayList<BankAccount> testAccounts = new ArrayList<BankAccount>();
        double total = testAdmin.getTotal(testAccounts);
        assertEquals(total, 0);
    }

    @Test
    public void testAverage(){
        BankAdmin testAdmin = new BankAdmin("");
        BankTeller teller = new BankTeller("teller");

        BankAccount a1 = new BankAccount("1", "");
        BankAccount a2 = new BankAccount("2", "");

        a1.deposit(10);
        teller.approveTransaction(a1, a1.getTransactions().get(0));

        a2.deposit(20);
        teller.approveTransaction(a2, a2.getTransactions().get(0));

        ArrayList<BankAccount> accounts = new ArrayList<>();
        accounts.add(a1);
        accounts.add(a2);

        double average = testAdmin.getAverage(accounts);
        assertEquals(15, average);
    }

    @Test
    public void testEmptyAverage(){
        BankAdmin testAdmin = new BankAdmin("");
        ArrayList<BankAccount> testAccounts = new ArrayList<BankAccount>();
        double average = testAdmin.getAverage(testAccounts);
        assertEquals(average, 0);
    }
    
    @Test
    public void testAdminViewReportedAccounts() {
        System.setIn(new java.io.ByteArrayInputStream("Main\npassword\n".getBytes()));

        MainMenu menu = new MainMenu();

        BankAccount acc = menu.getCurrentAccount();
        ReportedAccount report = new ReportedAccount(acc, "suspicious");

        menu.getReportedAccounts().add(report);

        assertEquals(1, menu.getReportedAccounts().size());
        assertTrue(menu.getReportedAccounts().get(0).formatReport().contains("suspicious"));
    }
}