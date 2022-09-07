package expensetracker.accounts;

import javax.swing.*;
import java.util.List;

/**
 * Manager class for JList of accounts.
 */
public class ListManager{
    JList list;
    DefaultListModel dlm;

    public ListManager(JList list){
        this.list = list;
        dlm = (DefaultListModel) list.getModel();
    }

    /**
     * Updates the list contents to correspond to provided list.
     * @param accountList list of accounts to be viewed
     */
    public void update(List<Account> accountList) {
        dlm.removeAllElements();
        for (Account acc: accountList) {
            dlm.addElement(acc.getName());
        }
    }
}
