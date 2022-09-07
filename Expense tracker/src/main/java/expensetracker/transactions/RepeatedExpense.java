package expensetracker.transactions;

/**
 * Extension of the Repeated class, represents a repeated expense.
 * Doesn't add any functionality on top of Repeated.
 */
public class RepeatedExpense extends Repeated{
    public RepeatedExpense(String name, double amount, int day, String note) {
        super(name, amount, day, note);
    }
}
