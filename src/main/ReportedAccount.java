package main;

public class ReportedAccount {
    private String username;
    private int ID;
    private String reportReason;

    public ReportedAccount(BankAccount account, String reportReason) {
        this.username = account.getName();
        this.ID = account.getID();
        this.reportReason = reportReason;
    }

    public String formatReport() {
        return "(ID: " + this.ID + ") " + this.username + " | Reason: " + this.reportReason;
    }
}
