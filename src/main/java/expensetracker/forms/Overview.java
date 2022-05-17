package expensetracker.forms;

import expensetracker.transactions.Income;
import expensetracker.transactions.Transaction;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Part of the main form.
 * Displays an overview of all the transactions, sum of incomes/expenses and overall balance.
 */
public class Overview extends JPanel {
    MainForm main;
    JLabel incomeNumLabel;
    JLabel expenseNumLabel;
    JLabel sumNumLabel;

    /**
     * Constructs the overview's GUI.
     * Initially sets all numbers to 0.
     * @param main main form
     */
    public Overview(MainForm main) {
        this.main = main;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel overviewLabel = new JLabel("Celkový přehled");
        add(overviewLabel, gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel incomeTextLabel = new JLabel("Příjmy:");
        add(incomeTextLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        incomeNumLabel = new JLabel("0");
        add(incomeNumLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel expenseTextLabel = new JLabel("Výdaje:");
        add(expenseTextLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        expenseNumLabel = new JLabel("0");
        add(expenseNumLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel sumTextLabel = new JLabel("Bilance:");
        add(sumTextLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        sumNumLabel = new JLabel("0");
        add(sumNumLabel, gbc);
    }

    /**
     * Updates the displayed values to correspond with transactions present in the table.
     * Is called after adding and removing a transaction.
     */
    public void update() {
        List<Transaction> transactionList = Overview.this.main.getExpenseView().getTableModel().getTransactionList();

        double incomes = 0;
        double expenses = 0;

        for (Transaction transaction: transactionList) {
            if (transaction.getClass().equals(Income.class)) {
                incomes += transaction.getAmount();
            } else {
                expenses += transaction.getAmount();
            }
        }

        incomeNumLabel.setText("" + incomes);
        expenseNumLabel.setText("" + expenses);
        sumNumLabel.setText("" + (incomes - expenses));
    }
}
