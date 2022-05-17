package expensetracker.table;

import expensetracker.transactions.CustDate;
import expensetracker.transactions.Expense;
import expensetracker.transactions.Income;
import expensetracker.transactions.Transaction;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Static class responsible for loading transactions from "transaction.csv" file in the root directory.
 * Loads the content of the file and checks if it is correct (same as output of Saver.save).
 * Message dialogs are shown when the file does not exist or the data is in wrong format/corrupt.
 */
public class Loader {
    /**
     * Loads transactions from the file into tableModel.
     * @param tableModel TableModel to which transactions are added
     * @throws FileNotFoundException thrown when the file does not exist
     * @throws ParseException thrown when the data is incorrect
     * @throws NumberFormatException thrown when the data is incorrect
     * @throws  IOException
     */
    public static void load(ExpenseTableModel tableModel) throws IOException, ParseException, NumberFormatException {
        clear(tableModel);

        BufferedReader br = new BufferedReader(new FileReader("transactions.csv"));
        String[] lines = br.lines().toArray(String[]::new);

        for (String line: lines) {
            String[] tokens = line.split(Saver.DELIM, -1);
            Transaction transaction;

            double amount = Double.parseDouble(tokens[2]);
            CustDate date = new CustDate(tokens[3]);

            if (tokens[0].equals("0")) {
                transaction = new Expense(tokens[1], amount, date, tokens[4]);
            } else {
                transaction = new Income(tokens[1], amount, date, tokens[4]);
            }

            tableModel.addTransaction(transaction);
        }
        br.close();
    }

    /**
     * Clears all transactions from the tableModel provided.
     * Used to load into clean state.
     * @param tableModel TableModel to be cleared
     */
    private static void clear(ExpenseTableModel tableModel) {
        List<Transaction> transactionList = new ArrayList<>(tableModel.getTransactionList());
        for (Transaction transaction: transactionList) {
            tableModel.removeTransaction(transaction);
        }
    }
}
