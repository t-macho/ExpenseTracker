package expensetracker.table;

import expensetracker.forms.MainForm;
import expensetracker.transactions.*;

import javax.swing.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Static class responsible for loading transactions from either "transactions_{account_name}.txt" if an account is selected,
 * or it opens up a FileChooser dialog in which the user can manually select the desired data file.
 * Checks for format validity and displays an error dialog if something is wrong.
 * Also loads and manager repeated payments - Saver.save() also appends the date on which the data file was saved and
 * load() then adds all payments that happened in the period between last date - today for each repeated payment.
 */
public class Loader {
    /**
     * Loads transactions from the file into tableModel. Also manages repeated payments.
     * @param expenseTableModel TableModel to which transactions are added
     * @param repeatedTableModel TableModel to which repeated payments are added
     * @throws FileNotFoundException thrown when the file does not exist
     * @throws ParseException thrown when the data is incorrect
     * @throws NumberFormatException thrown when the data is incorrect
     * @throws  IOException
     */
    public static void load(ExpenseTableModel expenseTableModel, RepeatedTableModel repeatedTableModel, String accountName) throws IOException, ParseException, NumberFormatException {
        clear(expenseTableModel);
        clear(repeatedTableModel);

        File transactionsFile;
        if (accountName == null) {
            //opens up the dialog where user can select path to file
            JFileChooser fileChooser = new JFileChooser();
            if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                transactionsFile = fileChooser.getSelectedFile();
            } else {
                return;
            }
        } else {
            //if an account is selected, "transactions_{accountName}.txt" is loaded
            String filePath =  "transactions_" + accountName + ".txt";
            transactionsFile = new File(filePath);
        }

        if (!transactionsFile.exists()) {
            transactionsFile.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(transactionsFile));
            bw.write(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
            bw.close();
        }

        BufferedReader br = new BufferedReader(new FileReader(transactionsFile));
        String[] lines = br.lines().toArray(String[]::new);

        for (int i = 0; i < lines.length - 1; i++) {
            String[] tokens = lines[i].split(Saver.DELIM, -1);
            Transaction transaction = null;

            double amount = Double.parseDouble(tokens[2]);

            if (tokens[0].equals("2")){
                int day = Integer.parseInt(tokens[3]);
                Repeated repeated = new RepeatedExpense(tokens[1], amount, day, tokens[4]);

                repeatedTableModel.addRepeated(repeated);
            } else if (tokens[0].equals("3")) {
                int day = Integer.parseInt(tokens[3]);
                Repeated repeated = new RepeatedIncome(tokens[1], amount, day, tokens[4]);

                repeatedTableModel.addRepeated(repeated);
            } else {
                CustDate date = new CustDate(tokens[3]);

                if (tokens[0].equals("0")) {
                    transaction = new Expense(tokens[1], amount, date, tokens[4]);
                } else if (tokens[0].equals("1")){
                    transaction = new Income(tokens[1], amount, date, tokens[4]);
                }
                expenseTableModel.addTransaction(transaction);
            }
        }
        CustDate lastDate = new CustDate(lines[lines.length-1]);
        CustDate today = new CustDate(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));

        //processing of the repeated payments
        if (today.compareTo(lastDate) > 0) {
            for (Repeated repeated: repeatedTableModel.getRepeatedList()) {
                boolean skipFirst = lastDate.getDay() >= repeated.getDay();
                boolean skipLast = today.getDay() < repeated.getDay();

                for (int year = lastDate.getYear(); year <= today.getYear(); year++) {
                    for (int month = 1; month <= 12; month++) {
                        if (year == lastDate.getYear() && month < lastDate.getMonth()) {
                            continue;
                        } else if (year == today.getYear() && month > today.getMonth()) {
                            continue;
                        } else if (skipFirst && year == lastDate.getYear() && month == lastDate.getMonth()) {
                            continue;
                        } else if (skipLast && year == today.getYear() && month == today.getMonth()) {
                            continue;
                        } else {
                            if (repeated.getClass().equals(RepeatedExpense.class)){
                                expenseTableModel.addTransaction(new Expense(repeated.getName(), repeated.getAmount(), new CustDate(""+repeated.getDay()+"."+month+"."+year), repeated.getNote()));
                            } else {
                                expenseTableModel.addTransaction(new Income(repeated.getName(), repeated.getAmount(), new CustDate(""+repeated.getDay()+"."+month+"."+year), repeated.getNote()));
                            }
                        }
                    }
                }

            }
        }

        br.close();
    }

    /**
     * Clears all transactions from the tableModel provided.
     * Used to load into clean state.
     * @param tableModel TableModel to be cleared
     */
    public static void clear(ExpenseTableModel tableModel) {
        List<Transaction> transactionList = new ArrayList<>(tableModel.getTransactionList());
        for (Transaction transaction: transactionList) {
            tableModel.removeTransaction(transaction);
        }
    }

    /**
     * Clears all transactions from the tableModel provided.
     * Used to load into clean state.
     * @param tableModel TableModel to be cleared
     */
    public static void clear(RepeatedTableModel tableModel) {
        List<Repeated> repeatedList = new ArrayList<>(tableModel.getRepeatedList());
        for (Repeated repeated: repeatedList) {
            tableModel.removeRepeated(repeated);
        }
    }
}
