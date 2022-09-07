package expensetracker.transactions;

/**
 * Abstract class for transactions.
 * Contains all the required fields, i.e name of the transaction, amount, date and optional note.
 */
public abstract class Transaction {
    String name = "";
    double amount = 0;
    CustDate date = null;
    String note = "";

    /**
     * Constructor with all fields.
     * @param name name of the transaction
     * @param amount amount of money in the transaction
     * @param date date of the transaction
     * @param note optional note
     */
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
