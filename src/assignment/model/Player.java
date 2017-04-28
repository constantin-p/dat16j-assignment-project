package assignment.model;

import assignment.db.Database;
import assignment.db.Storable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Player implements Storable {

    private static final String DATE_FORMAT = "dd-MM-yyyy";

    private String id;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty email;
    private ObjectProperty<LocalDate> dateOfBirth;

    public Player() {
        this.firstName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.dateOfBirth = new SimpleObjectProperty<>(LocalDate.now());
    }

    public Player(String id, String firstName, String lastName, String email, LocalDate dateOfBirth) {
        this.id = id;
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.lastName = new SimpleStringProperty(lastName);
        this.dateOfBirth = new SimpleObjectProperty<>(dateOfBirth);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth.get();
    }

    public ObjectProperty<LocalDate> dateOfBirthProperty() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }




    /*
     *  DB integration
     */
    @Override
    public HashMap<String, String> deconstruct() {
        HashMap<String, String> values = new HashMap<String, String>();

        values.put("first_name", this.firstName.getValue());
        values.put("last_name", this.lastName.getValue());
        values.put("email", this.email.getValue());
        values.put("date_of_birth", DateTimeFormatter.ofPattern(DATE_FORMAT)
                .format(this.dateOfBirth.getValue()));

        return values;
    }

    public static Player construct(HashMap<String, String> valuesMap) {

        String id = valuesMap.get("id");
        String firstName = valuesMap.get("first_name");
        String lastName = valuesMap.get("last_name");
        String email = valuesMap.get("email");

        LocalDate dateOfBirth = LocalDate.parse(valuesMap.get("date_of_birth"),
                DateTimeFormatter.ofPattern(DATE_FORMAT));

        return new Player(id, firstName, lastName, email, dateOfBirth);
    }

    /*
     *  DB helpers
     */
    public static List<Player> dbGetAll() {
        List<Player> result = new ArrayList<>();

        try {
            List<HashMap<String, String>> returnList = Database.getTable("players")
                    .getAll(Arrays.asList("id", "first_name", "last_name", "email", "date_of_birth"),
                            new HashMap<>(), new HashMap<>());

            returnList.forEach((HashMap<String, String> valuesMap) -> {
                result.add(Player.construct(valuesMap));
            });
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    public static Player dbGet(String id) {
        HashMap<String, String> searchQuery = new HashMap<>();
        searchQuery.put("id", id);

        try {
            HashMap<String, String> returnValues = Database.getTable("players")
                    .get(Arrays.asList("id", "first_name", "last_name", "email", "date_of_birth"),
                            searchQuery, new HashMap<>());

            if (returnValues.get("id") != null && returnValues.get("id").equals(id)) {
                return Player.construct(returnValues);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Player dbGetByEmail(String email) {
        HashMap<String, String> searchQuery = new HashMap<>();
        searchQuery.put("email", email);

        try {
            HashMap<String, String> returnValues = Database.getTable("players")
                    .get(Arrays.asList("id", "first_name", "last_name", "email", "date_of_birth"),
                            searchQuery, new HashMap<>());

            if (returnValues.get("email").equals(email)) {
                return Player.construct(returnValues);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int dbInsert(Player player) {
        try {
            return Database.getTable("players")
                    .insert(player.deconstruct());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // For debugging
    @Override
    public String toString() {
        return this.getClass().getCanonicalName()
                + "[id: " + this.id
                + ", firstName: " + this.firstName.getValue()
                + ", email: " + this.email.getValue()
                + ", dateOfBirth: " + this.dateOfBirth.getValue() + "]";
    }
}
