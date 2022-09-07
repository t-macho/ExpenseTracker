package expensetracker.forms;

import expensetracker.dialogs.EditDialog;
import expensetracker.table.ExpenseTableModel;
import expensetracker.table.ExpenseTable;
import expensetracker.transactions.Transaction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Main part of the whole GUI.
 * Table view of all the added transactions.
 * Rows are conditionally filtered to show whether the transaction is an expense or income.
 * Double-clicking a transaction opens EditDialog.
 * The table can be sorted by clicking on the table's header.
 * Table is scrollable.
 */
public class ExpenseView extends JPanel {
    MainForm main;
    ExpenseTable table;
    ExpenseTableModel tableModel;

    public ExpenseTable getTable() {
        return table;
    }

    public ExpenseTableModel getTableModel() {
        return tableModel;
    }

    /**
     * Contructs the table's GUI.
     * The table is placed into JScrollPane to make it scrollable.
     * There is a listener on the whole table that after double-clicking a transaction opens an EditDialog.
     * @param main main frame
     */
    public ExpenseView(MainForm main) {
        this.main = main;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        tableModel = new ExpenseTableModel(new Object[]{"Název", "Částka", "Datum"}, this);
        table = new ExpenseTable(tableModel, this);

        JScrollPane scrollPane = new JScrollPane(
                table,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        add(scrollPane, gbc);

        setMinimumSize(table.getPreferredSize());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Transaction transaction = tableModel.getTransactionAt(table.getSelectedRow());
                    EditDialog editDialog = new EditDialog(main, transaction);
                }
            }
        });
    }

    public MainForm getMain() {
        return main;
    }
}
