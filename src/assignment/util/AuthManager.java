package assignment.util;

import assignment.db.Database;
import assignment.model.AuthAccount;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.util.Arrays;
import java.util.HashMap;

public class AuthManager {

    public AuthAccount currentUser;
    public Runnable initRootLayout;
    public Runnable initLoginLayout;
    public Runnable initRegisterLayout;

    public AuthManager(Runnable initRootLayout, Runnable initLoginLayout, Runnable initRegisterLayout) {
        this.initRootLayout = initRootLayout;
        this.initLoginLayout = initLoginLayout;
        this.initRegisterLayout = initRegisterLayout;
    }

    public Response login(String username, String password) {
        try {
            HashMap<String, String> searchQuery = new HashMap<>();
            searchQuery.put("username", username);

            HashMap<String, String> returnValues = Database.getTable("accounts")
                    .get(Arrays.asList("username", "hash"), searchQuery);

            if (returnValues.get("hash") == null) {
                return new Response(false, ValidationHandler.ERROR_AUTH_USERNAME_NONEXISTENT);
            } if (Auth.validate(password, returnValues.get("hash"))) {
                // Set the current user
                currentUser = AuthAccount.construct(returnValues);
                return new Response(true);
            } else {
                return new Response(false, ValidationHandler.ERROR_AUTH_INVALID);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, e.getMessage());
        }
    }

    public Response register(String username, String password) {
        try {
            HashMap<String, String> entry = new HashMap<>();
            entry.put("username", username);
            entry.put("hash", Auth.hash(password));

            int returnValue = Database.getTable("accounts").insert(entry);

            if (returnValue == 1) {
                return new Response(true);
            }

            // Invalid response
            return new Response(false);
        } catch (MySQLIntegrityConstraintViolationException e) {
            return new Response(false, ValidationHandler.ERROR_AUTH_USERNAME_DUPLICATE);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, e.getMessage());
        }
    }

    public void signOut() {
        currentUser = null;
        initLoginLayout.run();
    }
}
