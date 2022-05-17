package expensetracker.transactions;

public class Income extends Transaction {
    public Income(String name, double amount, CustDate date, String note) {
        super(name, amount, date, note);
    }
}
