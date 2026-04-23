package main;

public class Transaction {

    public enum Status { 
        PENDING, 
        APPROVED, 
        DENIED,
        TRANSFERED
    }
    
    public enum Type { 
        DEPOSIT, 
        WITHDRAWAL,
        TRANSFER_IN,
        TRANSFER_OUT
    }

    private double amount;
    private String note;
    private Status status;
    private Type type;

    public Transaction(double amount, Type type) {
        this.amount = amount;
        this.type = type;
        this.note = "";
        this.status = Status.PENDING;
    }

    public double getAmount() { 
        return amount; 
    }

    public Type getType() { 
        return type; 
    }

    public Status getStatus() { 
        return status; 
    }

    public String getNote() { 
        return note; 
    }

    public void setNote(String note) { 
        this.note = note; 
    }

    public void approve() { 
        status = Status.APPROVED; 
    }

    public void deny() { 
        status = Status.DENIED; 
    }

    public void transfer() {
        status = Status.TRANSFERED;
    }

    public boolean isPending() { 
        return status == Status.PENDING; 
    }

    public boolean isApproved() { 
        return status == Status.APPROVED; 
    }

    public boolean isDenied() { 
        return status == Status.DENIED; 
    }
    
}
