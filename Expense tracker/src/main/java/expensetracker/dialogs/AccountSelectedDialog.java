package expensetracker.dialogs;

import expensetracker.accounts.AccountManager;
import expensetracker.accounts.ListManager;
import expensetracker.accounts.PasswordManager;
import expensetracker.forms.MainForm;
import expensetracker.table.Loader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;

/**
 * Dialog that's displayed after user selects an account to log into.
 * Also allows to delete the account if password is known.
 */
public class AccountSelectedDialog extends JDialog {
    MainForm main;
    AccountManager accManager;
    String accountName;
    ListManager listManager;

    public AccountSelectedDialog(MainForm main, String accountName, ListManager listManager) {
        super(main, "Přihlášení k '" + accountName + "'", true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.main = main;
        this.accManager = main.getAccManager();
        this.accountName = accountName;
        this.listManager = listManager;
        run();
    }

    private void run() {
        Container pane = getContentPane();
        pane.setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(200, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;

        JLabel pwdLabel = new JLabel("Heslo:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        pane.add(pwdLabel, gbc);

        JTextField pwdField = new JTextField();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        pane.add(pwdField, gbc);

        JButton confirmButton = new JButton("Přihlásit");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        pane.add(confirmButton, gbc);

        JButton deleteButton = new JButton("Smazat");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        pane.add(deleteButton, gbc);

        //passwords are checked using PasswordManager
        confirmButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                String pwdHash = accManager.getHashByName(accountName);
                if (PasswordManager.validatePassword(pwdField.getText(), pwdHash)) {
                    main.setSelectedAccount(accountName);
                    try {
                        Loader.load(main.getExpenseView().getTableModel(), main.getRepeatedView().getTableModel(), main.getSelectedAccount());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                    AccountSelectedDialog.this.dispose();
                } else {
                    JOptionPane.showMessageDialog(AccountSelectedDialog.this, "Špatné heslo", "Chyba", 0);
                }
            }
        });

        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                String pwdHash = accManager.getHashByName(accountName);
                if (PasswordManager.validatePassword(pwdField.getText(), pwdHash)) {
                    accManager.deleteAccount(accountName);
                    listManager.update(accManager.getAccountList());
                    AccountSelectedDialog.this.dispose();
                } else {
                    JOptionPane.showMessageDialog(AccountSelectedDialog.this, "Špatné heslo", "Chyba", 0);
                }
            }
        });

        pack();
        //sets the position of the dialog to the center of the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
