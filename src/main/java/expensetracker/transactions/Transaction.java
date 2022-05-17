package expensetracker.transactions;

public abstract class Transaction {
    String name = "";
    double amount = 0;
    CustDate date = null;
    String note = "";

    public Transaction(String name, double amount, CustDate date, String note) {
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.note = note;
    }

    public String getName() {
        return name;
    }
    public double getAmount() {
        return amount;
    }
    public CustDate getDate() {
        return date;
    }

    public String getNote() {
        return note;
    }
    @Override
    public String toString() {
        return name + " " + amount + " " + date;
    }
}
