package expensetracker.transactions;

/**
 * Extension of the Transaction class, represents an income.
 * Doesn't add any functionality on top of Transaction.
 */
public class Income extends Transaction {
    public Income(String name, double amount, CustDate date, String note) {
        super(name, amount, date, note);
    }
}
