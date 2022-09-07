package expensetracker.dialogs;

import expensetracker.accounts.AccountManager;
import expensetracker.accounts.ListManager;
import expensetracker.forms.MainForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Dialog that allows the creation of new user accounts.
 * Has two input fields, name and password.
 * The data provided is passed to AccountManager to be saved.
 */
public class NewAccountDialog extends JDialog {
    MainForm main;
    ListManager listManager;
    AccountManager accManager;
    JTextField nameField;
    JTextField pwdField;

    public NewAccountDialog(MainForm main, ListManager listManager, AccountManager accManager) {
        super(main, "Nový účet", true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.main = main;
        this.listManager = listManager;
        this.accManager = accManager;
        run();
    }

    private void run() {
        Container pane = getContentPane();
        pane.setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(170, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Jméno účtu:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        pane.add(nameLabel, gbc);

        nameField = new JTextField();
        gbc.gridx = 0;
        gbc.gridy = 1;
        pane.add(nameField, gbc);

        JLabel pwdLabel = new JLabel("Heslo:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        pane.add(pwdLabel, gbc);

        pwdField = new JTextField();
        gbc.gridx = 0;
        gbc.gridy = 3;
        pane.add(pwdField, gbc);

        JButton confirmBtn = new JButton("Vytvořit");
        gbc.gridx = 0;
        gbc.gridy = 4;
        pane.add(confirmBtn, gbc);

        confirmBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (validateCredentials()) {
                    JOptionPane.showMessageDialog(NewAccountDialog.this, "Heslo nelze obnovit. Uložte si ho na bezpečné místo!", "Upozornění!", JOptionPane.WARNING_MESSAGE);
                    NewAccountDialog.this.main.getAccManager().addAccount(
                            nameField.getText(),
                            pwdField.getText()
                    );
                    listManager.update(NewAccountDialog.this.accManager.getAccountList());
                    NewAccountDialog.this.dispose();
                } else {
                    JOptionPane.showMessageDialog(NewAccountDialog.this, "Neplatně vyplněné údaje!", "Chyba!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        pack();
        //sets the position of the dialog to the center of the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean validateCredentials() {
        return !(nameField.getText().equals("") || pwdField.getText().equals(""));
    }
}
