package expensetracker.accounts;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class responsible for managing accounts. Holds and loads/saves the accounts from/to accounts.txt file.
 * Also provides interface for adding and removing accounts.
 */
public class AccountManager {
    final String FILE_NAME = "accounts.txt";
    final String DELIM = ",";

    public List<Account> getAccountList() {
        return accountList;
    }

    List<Account> accountList;

    public AccountManager() {
        this.accountList = new ArrayList<>();
    }

    /**
     * Loads accounts from accounts.txt file. Puts them into the accountList list.
     */
    public void loadAccounts() {
        try (Scanner sc = new Scanner(new File(FILE_NAME))) {
            while (sc.hasNext()) {
                String line = sc.nextLine();
                String[] tokens = line.split(DELIM);

                accountList.add(new Account(tokens[0], tokens[1]));
            }
        } catch (FileNotFoundException e) {
            return;
        }
    }

    /**
     * Saves accounts to the accounts.txt file.
     */
    public void saveAccounts() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(FILE_NAME)))) {
            for (Account acc: accountList) {
                bw.write(acc.name + DELIM + acc.hash);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds an account to the manager. The password is hashed using PasswordManager.
     * @param name name of the account
     * @param password raw input password
     */
    public void addAccount(String name, String password) {
        String hash = PasswordManager.hashPassword(password);
        accountList.add(new Account(name, hash));
        saveAccounts();
    }

    /**
     * Utility method for retrieving accounts hash by its name.
     * @param name name of the account
     * @return
     */
    public String getHashByName(String name) {
        for (Account acc: accountList) {
            if (acc.getName().equals(name)) {
                return acc.getHash();
            }
        }
        return null;
    }

    /**
     * Deletes an account of given name.
     * @param name name of the account
     */
    public void deleteAccount(String name) {
        Account toRemove = null;
        for (Account acc: accountList) {
            if (acc.getName().equals(name)) {
                toRemove = acc;
            }
        }
        accountList.remove(toRemove);
        saveAccounts();
    }
}
