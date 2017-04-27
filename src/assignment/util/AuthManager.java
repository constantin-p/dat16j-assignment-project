package assignment.util;

import assignment.model.AuthAccount;

public class AuthManager {
    public AuthAccount currentUser;

    public AuthManager() {}

    public Response login(String username, String password) {
        currentUser = new AuthAccount();
        return new Response(true, ValidationHandler.ERROR_AUTH_INVALID);
    }

    public Response register(String username, String password) {
        return new Response(true, ValidationHandler.ERROR_AUTH_USERNAME_DUPLICATE);
    }
}
