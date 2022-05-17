package expensetracker.dialogs;

import expensetracker.forms.MainForm;
import expensetracker.transactions.CustDate;
import expensetracker.transactions.Expense;
import expensetracker.transactions.Income;
import expensetracker.transactions.Transaction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;

/**
 * Dialog responsible for adding new transactions.
 */
public class AddDialog extends JDialog {
    MainForm main;

    /**
     * Constructor of the dialog.
     * Only runs the run() method after setting the default close operation.
     * @param main main frame, parent of the dialog
     */
    public AddDialog(MainForm main) {
        super(main, "Přidat transakci", true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.main = main;
        run();
    }

    /**
     * Creates and displays the dialog frame.
     * Dialog uses listener on the only button to create and add the transaction from provided information.
     * There are four text fields for name, amount, date (all mandatory) and optional note.
     * There is also a radio button that is used to indicate whether the transaction is an expense or income.
     */
    private void run() {
        Container pane = getContentPane();
        pane.setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(170, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
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

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JLabel dateLabel = new JLabel("Datum (dd.mm.yyyy):");
        pane.add(dateLabel, gbc);


        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JTextField dateField = new JTextField();
        pane.add(dateField, gbc);

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

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        JButton confirmButton = new JButton("Přidat");
        pane.add(confirmButton, gbc);

        confirmButton.addMouseListener(new MouseAdapter() {
            /**
             * Mouse press listener on the confirm button.
             * After the event triggers, input fields are parsed.
             * If there is any error during parsing, message dialog is shown to let the user know what went wrong.
             * If all the information is correct, new transaction is created and added.
             * @param e the event to be processed
             */
            @Override
            public void mousePressed(MouseEvent e) {
                String name = nameField.getText();
                String amountRaw = amountField.getText();
                String dateRaw = dateField.getText();
                String note = noteArea.getText();

                Double amount = 0.0;
                CustDate date = null;

                if (name == null || name.equals("")) {
                    JOptionPane.showMessageDialog(AddDialog.this, "Název nemůže být prázdný.");
                    return;
                }

                try {
                    amount = Double.parseDouble(amountRaw);
                    date = new CustDate(dateRaw);

                    if (amount <= 0) {
                        JOptionPane.showMessageDialog(AddDialog.this, "Neplatná částka.");
                        return;
                    }

                    Transaction transaction;
                    if (incomeRadio.isSelected()) {
                        transaction = new Income(name, amount, date, note);
                    } else {
                        transaction = new Expense(name, amount, date, note);
                    }
                    main.getExpenseView().getTableModel().addTransaction(transaction);
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AddDialog.this, "Neplatná částka.");
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(AddDialog.this, "Neplatné datum.");
                }
            }
        });

        pack();
        //sets the position of the dialog to the center of the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
