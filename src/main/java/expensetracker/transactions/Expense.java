package expensetracker.transactions;

/**
 * Extension of the Transaction class, represents an expense.
 * Doesn't add any functionality on top of Transaction.
 */
public class Expense extends Transaction {
    public Expense(String name, double amount, CustDate date, String note) {
        super(name, amount, date, note);
    }
}
