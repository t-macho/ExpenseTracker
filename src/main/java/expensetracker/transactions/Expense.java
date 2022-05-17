package expensetracker.transactions;

public class Expense extends Transaction {
    public Expense(String name, double amount, CustDate date, String note) {
        super(name, amount, date, note);
    }
}
