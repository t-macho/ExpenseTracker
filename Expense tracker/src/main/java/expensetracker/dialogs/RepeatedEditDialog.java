package expensetracker.dialogs;

import expensetracker.forms.MainForm;
import expensetracker.table.ExpenseTableModel;
import expensetracker.table.RepeatedTableModel;
import expensetracker.transactions.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;

/**
 * Dialog responsible for editing existing repeated payments.
 */
public class RepeatedEditDialog extends JDialog {
    MainForm main;
    Repeated repeated;

    public RepeatedEditDialog(MainForm main, Repeated repeated) {
        super(main, "Upravit opakovanou platbu", true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.main = main;
        this.repeated = repeated;
        run();
    }

    private void run() {
        Container pane = getContentPane();
        pane.setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(170,0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel nameLabel = new JLabel("Název:");
        pane.add(nameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        JTextField nameField = new JTextField();
        pane.add(nameField, gbc);
        nameField.setText(repeated.getName());

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JLabel amountLabel = new JLabel("Částka:");
        pane.add(amountLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JTextField amountField = new JTextField();
        pane.add(amountField, gbc);
        amountField.setText(String.valueOf(repeated.getAmount()));

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JLabel dayLabel = new JLabel("Den:");
        pane.add(dayLabel, gbc);


        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JTextField dayField= new JTextField();
        pane.add(dayField, gbc);
        dayField.setText(String.valueOf(repeated.getDay()));

        ButtonGroup group = new ButtonGroup();

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        JRadioButton expenseRadio = new JRadioButton("výdaj");
        pane.add(expenseRadio, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        JRadioButton incomeRadio = new JRadioButton("příjem");
        pane.add(incomeRadio, gbc);

        group.add(expenseRadio);
        group.add(incomeRadio);
        expenseRadio.setSelected(true);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        JLabel noteLabel = new JLabel("Poznámka:");
        pane.add(noteLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        JTextArea noteArea = new JTextArea();
        noteArea.setRows(4);
        noteArea.setLineWrap(true);
        pane.add(noteArea, gbc);
        noteArea.setText(repeated.getNote());

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        JButton confirmButton = new JButton("Potvrdit");
        pane.add(confirmButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        JButton deleteButton = new JButton("Smazat");
        pane.add(deleteButton, gbc);

        confirmButton.addMouseListener(new MouseAdapter() {
            /**
             * Mouse press listener on the confirm button.
             * Just like with AddDialog, input fields are parsed after triggering, potential errors are shown.
             * If provided information are correct, the old transaction is removed and new one is added.
             * @param e the event to be processed
             */
            @Override
            public void mousePressed(MouseEvent e) {
                String name = nameField.getText();
                String amountRaw = amountField.getText();
                String dayRaw = dayField.getText();
                String note = noteArea.getText();

                Double amount = 0.0;
                int day = 0;

                if (name == null || name.equals("")) {
                    JOptionPane.showMessageDialog(RepeatedEditDialog.this, "Název nemůže být prázdný.");
                    return;
                }

                try {
                    amount = Double.parseDouble(amountRaw);
                    day = Integer.parseInt(dayRaw);

                    if (amount <= 0) {
                        JOptionPane.showMessageDialog(RepeatedEditDialog.this, "Neplatná částka.");
                        return;
                    }

                    if (day < 1 || day > 28) {
                        JOptionPane.showMessageDialog(RepeatedEditDialog.this, "Pro opakovanou platbu je možné zadat den jen z rozmezí 1-28.");
                        return;
                    }

                    Repeated newRepeated;
                    if (expenseRadio.isSelected()) {
                        newRepeated = new RepeatedExpense(name, amount, day, note);
                    } else {
                        newRepeated = new RepeatedIncome(name, amount, day, note);
                    }

                    main.getRepeatedView().getTableModel().addRepeated(newRepeated);
                    main.getRepeatedView().getTableModel().removeRepeated(repeated);
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(RepeatedEditDialog.this, "Neplatná hodnota.");
                }
            }
        });

        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                RepeatedTableModel tableModel = main.getRepeatedView().getTableModel();
                Repeated repeated = tableModel.getRepeatedAt(main.getRepeatedView().getTable().getSelectedRow());
                main.getRepeatedView().getTableModel().removeRepeated(repeated);
                dispose();
            }
        });

        pack();
        //sets the location of the dialog to the center of the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
