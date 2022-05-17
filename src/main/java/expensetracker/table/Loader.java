package expensetracker.table;

import expensetracker.transactions.CustDate;
import expensetracker.transactions.Expense;
import expensetracker.transactions.Income;
import expensetracker.transactions.Transaction;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Loader {
    public static void load(ExpenseTableModel tableModel) throws FileNotFoundException, ParseException, NumberFormatException {
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
    }

    private static void clear(ExpenseTableModel tableModel) {
        List<Transaction> transactionList = new ArrayList<>(tableModel.getTransactionList());
        for (Transaction transaction: transactionList) {
            tableModel.removeTransaction(transaction);
        }
    }
}
