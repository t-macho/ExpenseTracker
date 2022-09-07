package expensetracker.forms;

import expensetracker.accounts.AccountManager;
import expensetracker.dialogs.AccountDialog;
import expensetracker.table.Loader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;

/**
 * Main form.
 * Contains all the other graphic parts - the table, action elements and overview panel.
 */
public class MainForm extends Frame{
    ExpenseView expenseView;
    ActionArea actionArea;
    Overview overview;
    AccountManager accManager;

    public RepeatedView getRepeatedView() {
        return repeatedView;
    }

    RepeatedView repeatedView;

    public JFrame getFrame() {
        return frame;
    }

    JFrame frame;
    String selectedAccount;

    public String getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(String selectedAccount) {
        this.selectedAccount = selectedAccount;
    }

    public ExpenseView getExpenseView() {
        return expenseView;
    }

    public AccountManager getAccManager() {
        return accManager;
    }

    /**
     * Constructs the GUI of the whole application.
     */
    public MainForm() {
        accManager = new AccountManager();
        accManager.loadAccounts();
        this.selectedAccount = null;
        createAndShowGUI();
    }

    /**
     * Method responsible for creating and showing the GUI.
     * Creates all the main form parts and puts them together in GridBagLayout.
     * First hides itself and shows the account selection dialog.
     */
    public void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("Sledovač výdajů");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = frame.getContentPane();

        pane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);

        gbc.weightx = 0.3;
        gbc.weighty = 1;

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        actionArea = new ActionArea(this);
        pane.add(actionArea, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        overview = new Overview(this);
        pane.add(overview, gbc);

        gbc.weightx = 1;
        gbc.weighty = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        expenseView = new ExpenseView(this);
        pane.add(expenseView, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        repeatedView = new RepeatedView(this);
        pane.add(repeatedView, gbc);
        repeatedView.setVisible(false);

        //open up the account selection dialog
        new AccountDialog(this);
        if (selectedAccount != null) {
            //if user logs into an account, the data is loaded automatically
            try {
                Loader.load(expenseView.getTableModel(), repeatedView.getTableModel(), selectedAccount);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        frame.pack();
        //center the window on screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public Overview getOverview() {
        return overview;
    }
}
