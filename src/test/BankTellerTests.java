package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import main.BankAccount;
import main.BankTeller;
import main.ReportedAccount;
import main.Transaction;

public class BankTellerTests {
	
	@Test
	public void testTellerApproveDeposit() {
	    BankAccount account = new BankAccount("test", "test");
	    BankTeller teller = new BankTeller("pw");

	    account.deposit(100);
	    Transaction t = account.getTransactions().get(0);

	    teller.approveTransaction(account, t);

	    assertEquals(100, account.getBalance(), 0.01);
	    assertTrue(t.isApproved());
	}
	
	@Test
	public void testTellerApproveWithdrawal() {
	    BankAccount account = new BankAccount("test", "test");
	    BankTeller teller = new BankTeller("pw");

	    account.deposit(100);
	    Transaction deposit = account.getTransactions().get(0);
	    teller.approveTransaction(account, deposit);

	    account.withdraw(40);
	    Transaction withdraw = account.getTransactions().get(1);

	    teller.approveTransaction(account, withdraw);

	    assertEquals(60, account.getBalance(), 0.01);
	    assertTrue(withdraw.isApproved());
	}
	
	@Test
	public void testTellerDenyTransaction() {
	    BankAccount account = new BankAccount("test", "test");
	    BankTeller teller = new BankTeller("pw");

	    account.deposit(100);
	    Transaction t = account.getTransactions().get(0);

	    teller.denyTransaction(t);

	    assertEquals(0, account.getBalance(), 0.01);
	    assertTrue(t.isDenied());
	}
	
	@Test
	public void testReportedAccountFormatting() {
	    BankAccount account = new BankAccount("alice", "pw");
	    ReportedAccount report = new ReportedAccount(account, "fraud");

	    String result = report.formatReport();

	    assertTrue(result.contains("alice"));
	    assertTrue(result.contains("fraud"));
	    assertTrue(result.contains(String.valueOf(account.getID())));
	}
	
}