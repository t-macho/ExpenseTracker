package expensetracker.filters;

import expensetracker.table.ExpenseTableModel;
import expensetracker.transactions.CustDate;
import expensetracker.transactions.Expense;
import expensetracker.transactions.Transaction;

import javax.swing.*;
import java.util.List;

public class ExpenseFilter extends RowFilter<ExpenseTableModel, Integer> {

    @Override
    public boolean include(Entry<? extends ExpenseTableModel, ? extends Integer> entry) {
        ExpenseTableModel tableModel = entry.getModel();
        int modelRow = entry.getIdentifier();
        String name = (String) tableModel.getValueAt(modelRow, 0);
        double amount = (double) tableModel.getValueAt(modelRow, 1);
        CustDate date = (CustDate) tableModel.getValueAt(modelRow, 2);

        List<Transaction> transactionList = tableModel.getTransactionList();

        for (Transaction transaction: transactionList) {
            if (
                    transaction.getName().equals(name)
                            && transaction.getAmount() == amount
                            && transaction.getDate().equals(date)
            ) {
                return transaction.getClass().equals(Expense.class);
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "výdaje";
    }
}
