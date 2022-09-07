package expensetracker.table;

import expensetracker.transactions.Expense;
import expensetracker.transactions.Repeated;
import expensetracker.transactions.RepeatedExpense;
import expensetracker.transactions.Transaction;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Static class responsible for saving transactions to either "transactions_{account_name}.txt" if an account is selected,
 * or it opens up a FileChooser dialog in which the user can manually select the desired data file.
 * Saves data in "type[DELIM]name[DELIM]amount[DELIM]date[DELIM]note" format.
 * DELIM is static field used as separator in the data file.
 */
public class Saver {
    public static final String DELIM = ",";

    /**
     * Saves transactions to desired file (either to an account file or where the user selects).
     * Saves data in "type[DELIM]name[DELIM]amount[DELIM]date[DELIM]note" format.
     * @param expenseTableModel TableModel from which the transactions are saved.
     * @param repeatedTableModel TableModel from which the repeated payments are saved.
     * @throws IOException
     */
    public static void save(ExpenseTableModel expenseTableModel, RepeatedTableModel repeatedTableModel, String accountName) throws IOException {
        File transactionsFile;
        if (accountName == null) {
            JFileChooser fileChooser = new JFileChooser();
            if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                transactionsFile = fileChooser.getSelectedFile();
            } else {
                return;
            }
        } else {
            String filePath =  "transactions_" + accountName + ".txt";
            transactionsFile = new File(filePath);
        }

        List<Transaction> transactions = expenseTableModel.getTransactionList();
        List<Repeated> repeatedList = repeatedTableModel.getRepeatedList();

        //delete file content
        new FileWriter(transactionsFile).close();
        BufferedWriter bw = new BufferedWriter(new FileWriter(transactionsFile, true));

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

        for (Repeated repeated: repeatedList) {
            int type = repeated.getClass().equals(RepeatedExpense.class)
                    ? 2
                    : 3;
            String line = String.format(
                    "%s%s%s%s%s%s%s%s%s\n",
                    type, DELIM,
                    repeated.getName(), DELIM,
                    repeated.getAmount(), DELIM,
                    repeated.getDay(), DELIM,
                    repeated.getNote()
            );

            bw.write(line);
        }

        //append current date to the end of the file
        String date = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        bw.write(date);
        bw.close();
    }
}
