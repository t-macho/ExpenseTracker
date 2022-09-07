package expensetracker.dialogs;

import expensetracker.accounts.AccountManager;
import expensetracker.accounts.ListManager;
import expensetracker.forms.MainForm;
import expensetracker.table.Loader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Dialog class which shows existing users' accounts and allows to create new accounts, log into existing ones or delete accounts.
 */
public class AccountDialog extends JDialog {
    MainForm main;
    AccountManager accManager;
    ListManager listManager;

    public AccountDialog(MainForm main) {
        super(main, "Výběr účtu", true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.main = main;
        this.accManager = main.getAccManager();
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

        JList accountList = new JList<String>(new DefaultListModel<>());
        listManager = new ListManager(accountList);
        accountList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(
                accountList,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        pane.add(scrollPane, gbc);

        JButton addButton = new JButton("Vytvořit nový účet");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        pane.add(addButton, gbc);

        JButton noAccButton = new JButton("Pokračovat bez účtu");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        pane.add(noAccButton, gbc);

        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new NewAccountDialog(main, listManager, accManager);
            }
        });

        //if no account is selected, no login is required
        noAccButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                main.setSelectedAccount(null);
                Loader.clear(main.getExpenseView().getTableModel());
                Loader.clear(main.getRepeatedView().getTableModel());
                AccountDialog.this.dispose();
            }
        });

        accountList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    new AccountSelectedDialog(main, (String) accountList.getSelectedValue(), listManager);
                    if (main.getSelectedAccount() != null) {
                        AccountDialog.this.dispose();
                    }
                }
            }
        });

        listManager.update(accManager.getAccountList());

        pack();
        //sets the position of the dialog to the center of the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
