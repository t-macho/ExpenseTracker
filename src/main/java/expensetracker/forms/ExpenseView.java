package expensetracker.forms;

import expensetracker.dialogs.EditDialog;
import expensetracker.table.ExpenseTableModel;
import expensetracker.table.Table;
import expensetracker.transactions.Transaction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ExpenseView extends JPanel {
    MainForm main;
    Table table;
    ExpenseTableModel tableModel;

    public Table getTable() {
        return table;
    }

    public ExpenseTableModel getTableModel() {
        return tableModel;
    }

    public ExpenseView(MainForm main) {
        this.main = main;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        tableModel = new ExpenseTableModel(new Object[]{"Název", "Částka", "Datum"}, this);
        table = new Table(tableModel, this);

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
