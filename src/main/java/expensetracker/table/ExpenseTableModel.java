package expensetracker.table;

import expensetracker.forms.ExpenseView;
import expensetracker.transactions.CustDate;
import expensetracker.transactions.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseTableModel extends DefaultTableModel {
    ExpenseView expenseView;
    List<Transaction> transactionList;
    Object[] columns;

    JTable table;

    public Object[] getColumns() {
        return columns;
    }

    public ExpenseTableModel(Object[] columns, ExpenseView expenseView) {
        super(columns, 0);
        this.columns = columns;
        this.transactionList = new ArrayList<>();
        this.expenseView = expenseView;
    }

    @Override
    public boolean isCellEditable(int row, int column){
        return false;
    }

    public void addTransaction(Transaction transaction) {
        super.addRow(new Object[] {transaction.getName(), transaction.getAmount(), transaction.getDate()});
        transactionList.add(transaction);
        this.expenseView.getMain().getOverview().update();
    }

    public void removeTransaction(Transaction transaction) {
        super.removeRow(transactionList.indexOf(transaction));
        transactionList.remove(transaction);
        this.expenseView.getMain().getOverview().update();
    }

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

    public Transaction getTransactionAt(int row) {
        int modelRow = table.convertRowIndexToModel(row);
        return transactionList.get(modelRow);
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }


}
