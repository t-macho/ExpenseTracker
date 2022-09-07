package expensetracker.transactions;

/**
 * Extension of the Repeated class, represents a repeated income.
 * Doesn't add any functionality on top of Repeated.
 */
public class RepeatedIncome extends Repeated{
    public RepeatedIncome(String name, double amount, int day, String note) {
        super(name, amount, day, note);
    }
}
