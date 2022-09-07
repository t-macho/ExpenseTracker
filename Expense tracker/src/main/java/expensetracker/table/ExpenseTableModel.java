package expensetracker.table;

import expensetracker.forms.ExpenseView;
import expensetracker.transactions.CustDate;
import expensetracker.transactions.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom TableModel.
 * Responsible for holding all the transactions in transactionList and the table itself.
 */
public class ExpenseTableModel extends DefaultTableModel {
    ExpenseView expenseView;
    List<Transaction> transactionList;
    Object[] columns;

    JTable table;

    public Object[] getColumns() {
        return columns;
    }

    /**
     * Constructs the ExpenseTableModel with given column names.
     * @param columns column names to be used in the table header
     * @param expenseView frame with the table in which this model is used
     */
    public ExpenseTableModel(Object[] columns, ExpenseView expenseView) {
        super(columns, 0);
        this.columns = columns;
        this.transactionList = new ArrayList<>();
        this.expenseView = expenseView;
    }

    //sets all the cells as uneditable
    @Override
    public boolean isCellEditable(int row, int column){
        return false;
    }

    /**
     * Adds the given transaction to model's list and to the table as a new row.
     * After adding the transaction, update() is called on the overview frame in order to update the displayed values.
     * @param transaction transaction to be added
     */
    public void addTransaction(Transaction transaction) {
        super.addRow(new Object[] {transaction.getName(), transaction.getAmount(), transaction.getDate()});
        transactionList.add(transaction);
        this.expenseView.getMain().getOverview().update();
    }

    /**
     * Removes the given transaction from transactionList and the table.
     * After removing, update() is called on the overview form in order to update the displayed values.
     * @param transaction transaction to be removed
     */
    public void removeTransaction(Transaction transaction) {
        super.removeRow(transactionList.indexOf(transaction));
        transactionList.remove(transaction);
        this.expenseView.getMain().getOverview().update();
    }

    /**
     * Return the class of given column.
     * First is string for name, followed by a double for amount, CustDate for date and another string for note.
     * @param columnIndex  the column being queried
     * @return class of the column
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0 -> {
                return String.class;
            }
            case 1 -> {
                return Double.class;
            }
            case 2 -> {
                return CustDate.class;
            }
        }
        return Object.class;
    }

    /**
     * Return transaction on given row of the table.
     * @param row
     * @return transaction on specified row
     */
    public Transaction getTransactionAt(int row) {
        int modelRow = table.convertRowIndexToModel(row);
        return transactionList.get(modelRow);
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }
}
