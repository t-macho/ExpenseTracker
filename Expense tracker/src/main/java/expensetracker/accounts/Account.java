package expensetracker.accounts;

/**
 * Class for a user account. Holds name and hash of the password.
 */
public class Account {
    String name;

    public String getName() {
        return name;
    }

    public String getHash() {
        return hash;
    }

    String hash;

    public Account(String name, String hash){
        this.name = name;
        this.hash = hash;
    }
}
