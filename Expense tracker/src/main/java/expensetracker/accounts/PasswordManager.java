package expensetracker.accounts;
//TODO
//

import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

/**
 * Static class that wraps encrypting/decrypting user passwords.
 */
public class PasswordManager {
    static Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder();

    /**
     * Returns hash of the given password.
     * @param password password to hash
     * @return hash of the password
     */
    public static String hashPassword(String password) {
        return encoder.encode(password);
    }

    /**
     * Validates if the given password corresponds to hash of the account's password.
     * @param input input password
     * @param hash true hashed password
     * @return true if hashed input matches hash of account's password
     */
    public static boolean validatePassword(String input, String hash) {
        return encoder.matches(input, hash);
    }
}
