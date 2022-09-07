package expensetracker.forms;

import expensetracker.dialogs.EditDialog;
import expensetracker.dialogs.RepeatedAddDialog;
import expensetracker.dialogs.RepeatedEditDialog;
import expensetracker.table.RepeatedTable;
import expensetracker.table.RepeatedTableModel;
import expensetracker.transactions.Repeated;
import expensetracker.transactions.Transaction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Part of the main form that displays all repeated payments present on the account.
 */
public class RepeatedView extends JPanel {
    MainForm main;

    public RepeatedTable getTable() {
        return table;
    }

    RepeatedTable table;

    public RepeatedTableModel getTableModel() {
        return tableModel;
    }

    RepeatedTableModel tableModel;

    public RepeatedView(MainForm main) {
        this.main = main;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        tableModel = new RepeatedTableModel();
        table = new RepeatedTable(tableModel, this);

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

        JButton addButton = new JButton("Přidat opakovanou platbu");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.05;
        gbc.weightx = 0.05;
        add(addButton, gbc);

        setMinimumSize(table.getPreferredSize());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Repeated repeated = tableModel.getRepeatedAt(table.getSelectedRow());
                    new RepeatedEditDialog(main, repeated);
                }
            }
        });

        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new RepeatedAddDialog(main);
            }
        });
    }
}
