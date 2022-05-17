package expensetracker.table;

import expensetracker.forms.ExpenseView;
import expensetracker.transactions.Expense;
import expensetracker.transactions.Transaction;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class Table extends JTable {
    ExpenseView expenseView;
    TableRowSorter rowSorter;
    public Table(ExpenseTableModel tableModel, ExpenseView expenseView) {
        super(tableModel);
        this.expenseView = expenseView;

        rowSorter = new TableRowSorter(tableModel);
        setRowSorter(rowSorter);

        tableModel.table = this;
        getColumn(tableModel.getColumns()[0]).setMinWidth(120);
        getColumn(tableModel.getColumns()[1]).setMinWidth(100);
        getColumn(tableModel.getColumns()[2]).setMinWidth(90);
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        ExpenseTableModel tableModel = expenseView.getTableModel();
        Transaction transaction = tableModel.getTransactionAt(row);

        c.setFont(new Font("font", Font.PLAIN, 14));

        if (transaction.getClass().equals(Expense.class)){
            c.setBackground(new Color(255, 112, 77));
        } else {
            c.setBackground(new Color(128, 255, 128));
        }
        return c;
    }

    public void setFilter(RowFilter filter) {
        this.rowSorter.setRowFilter(filter);
    }
}
