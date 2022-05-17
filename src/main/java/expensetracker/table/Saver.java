package expensetracker.table;

import expensetracker.transactions.Expense;
import expensetracker.transactions.Transaction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Saver {
    public static final String DELIM = ",";

    public static void save(ExpenseTableModel tableModel) throws IOException {
        List<Transaction> transactions = tableModel.getTransactionList();

        //delete file content
        new FileWriter("transactions.csv").close();
        BufferedWriter bw = new BufferedWriter(new FileWriter("transactions.csv", true));

        for (Transaction transaction: transactions) {
            //1 income, 0 expense
            int type = transaction.getClass().equals(Expense.class)
                    ? 0
                    : 1;
            String line = String.format(
                    "%s%s%s%s%s%s%s%s%s\n",
                    type, DELIM,
                    transaction.getName(), DELIM,
                    transaction.getAmount(), DELIM,
                    transaction.getDate(), DELIM,
                    transaction.getNote()
            );

            bw.write(line);
        }
        bw.close();
    }
}
