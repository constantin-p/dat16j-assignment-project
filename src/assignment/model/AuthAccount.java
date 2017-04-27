package assignment.model;

import assignment.db.Storable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashMap;

public class AuthAccount implements Storable {

    private StringProperty username;

    public AuthAccount (String username) {
        this.username = new SimpleStringProperty(username);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    /*
         *  DB integration
         */
    @Override
    public HashMap<String, String> deconstruct() {
        HashMap<String, String> values = new HashMap<String, String>();

        values.put("username", this.username.getValue());

        return values;
    }

    public static AuthAccount construct(HashMap<String, String> valuesMap) {
        String username = valuesMap.get("username");

        return new AuthAccount(username);
    }

    // For debugging
    @Override
    public String toString() {
        return this.getClass().getCanonicalName()
                + "[username: " + this.username + "]";
    }
}
