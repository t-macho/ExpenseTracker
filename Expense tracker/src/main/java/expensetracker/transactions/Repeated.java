package expensetracker.transactions;

/**
 * Abstract class for repeated payments.
 * Contains all the required fields, i.e name of the payment, amount, day on which the payment happens and optional note.
 */
public class Repeated {
    public String name;
    public double amount;
    public int day;
    public String note;

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public int getDay() {
        return day;
    }

    public String getNote() {
        return note;
    }

    public Repeated(String name, double amount, int day, String note) {
        this.name = name;
        this.amount = amount;
        this.day = day;
        this.note = note;
    }
}
