package expensetracker.table;

import expensetracker.forms.ExpenseView;
import expensetracker.transactions.Expense;
import expensetracker.transactions.Transaction;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;

/**
 * Custom JTable.
 * Responsible for properly displaying the transactions stored in table's model.
 */
public class ExpenseTable extends JTable {
    ExpenseView expenseView;
    TableRowSorter rowSorter;

    /**
     * Constructs a table with given TableModel.
     * Default row sorter is used for sorting functionality.
     * Custom filters are conditionally added to only display some transactions.
     * @param tableModel TableModel of the table
     * @param expenseView frame in which the table is placed
     */
    public ExpenseTable(ExpenseTableModel tableModel, ExpenseView expenseView) {
        super(tableModel);
        this.expenseView = expenseView;

        rowSorter = new TableRowSorter(tableModel);
        setRowSorter(rowSorter);

        tableModel.table = this;
        getColumn(tableModel.getColumns()[0]).setMinWidth(130);
        getColumn(tableModel.getColumns()[1]).setMinWidth(110);
        getColumn(tableModel.getColumns()[2]).setMinWidth(100);
    }

    /**
     * Sets bigger font for all cells.
     * Also colors rows depending on whether the row represents an income (green) or expense (red).
     * @param renderer  the <code>TableCellRenderer</code> to prepare
     * @param row       the row of the cell to render, where 0 is the first row
     * @param column    the column of the cell to render,
     *                  where 0 is the first column
     * @return prepared component
     */
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

    /**
     * Sets table's RowSorter to use one of the custom filters.
     * @param filter filter to be set in table's RowSorter
     */
    public void setFilter(RowFilter filter) {
        this.rowSorter.setRowFilter(filter);
    }
}
