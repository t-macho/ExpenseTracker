package expensetracker.filters;

import expensetracker.table.ExpenseTableModel;
import expensetracker.transactions.CustDate;
import expensetracker.transactions.Income;
import expensetracker.transactions.Transaction;

import javax.swing.*;
import java.util.List;

/**
 * Filter for RowSorter of the table.
 * Displays only the incomes, expenses are excluded.
 */
public class IncomeFilter extends RowFilter<ExpenseTableModel, Integer> {
    /**
     * Override of RowFilter include method.
     * Includes only incomes. Transaction is pulled from the used TableModel.
     * @param entry entry of the table
     * @return true for income, false for expense
     */
    @Override
    public boolean include(Entry<? extends ExpenseTableModel, ? extends Integer> entry) {
        ExpenseTableModel tableModel = entry.getModel();
        int modelRow = entry.getIdentifier();
        String name = (String) tableModel.getValueAt(modelRow, 0);
        double amount = (double) tableModel.getValueAt(modelRow, 1);
        CustDate date = (CustDate) tableModel.getValueAt(modelRow, 2);

        List<Transaction> transactionList = tableModel.getTransactionList();

        for (Transaction transaction: transactionList) {

            /*
            checking whether the transaction are nearly the same
            ignores notes, so there is a possibility of a bug when there are two nearly
            identical transactions, but it is unlikely to happen
            */
            if (
                transaction.getName().equals(name)
                && transaction.getAmount() == amount
                && transaction.getDate().equals(date)
            ) {
                return transaction.getClass().equals(Income.class);
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "příjmy";
    }
}
