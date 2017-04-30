package assignment.util;

import assignment.model.Team;
import assignment.util.Response;
import assignment.model.Player;
import javafx.scene.control.Control;
import javafx.scene.control.Label;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.YEARS;

public class ValidationHandler {

    // Error messages
    public static final String ERROR_DB_CONNECTION = "DB connection error";

    public static final String ERROR_AUTH_INVALID = "Invalid credentials";
    public static final String ERROR_AUTH_USERNAME_REQUIRED = "Username required";
    public static final String ERROR_AUTH_USERNAME_SHORT = "Username too short";
    public static final String ERROR_AUTH_USERNAME_LONG = "Username too long";
    public static final String ERROR_AUTH_USERNAME_INVALID = "Invalid username (non-alphanumeric)";
    public static final String ERROR_AUTH_USERNAME_NONEXISTENT = "Username not registered";
    public static final String ERROR_AUTH_USERNAME_DUPLICATE = "Username already registered";
    public static final String ERROR_AUTH_PASSWORD_REQUIRED = "Password required";
    public static final String ERROR_AUTH_PASSWORD_SHORT = "Password too short";
    public static final String ERROR_AUTH_PASSWORD_LONG = "Password too long";
    public static final String ERROR_AUTH_PASSWORD_INVALID = "Invalid password (non-alphanumeric)";
    public static final String ERROR_AUTH_PASSWORD_DIFFERENT = "Passwords do not match";

    public static final String ERROR_TOURNAMENT_NAME_REQUIRED = "Name required";
    public static final String ERROR_TOURNAMENT_NAME_SHORT = "Name too short";
    public static final String ERROR_TOURNAMENT_NAME_LONG = "Name too long";
    public static final String ERROR_TOURNAMENT_NAME_INVALID = "Invalid name (non-alphanumeric)";
    public static final String ERROR_TOURNAMENT_NAME_DUPLICATE = "Name already registered";

    public static final String ERROR_TEAM_NAME_REQUIRED = "Name required";
    public static final String ERROR_TEAM_NAME_SHORT = "Name too short";
    public static final String ERROR_TEAM_NAME_LONG = "Name too long";
    public static final String ERROR_TEAM_NAME_INVALID = "Invalid name (non-alphanumeric)";
    public static final String ERROR_TEAM_NAME_DUPLICATE = "Name already registered";
    public static final String ERROR_TEAM_PLAYER_REQUIRED = "Player required";


    public static final String ERROR_PLAYER_FIRSTNAME_REQUIRED = "First name required";
    public static final String ERROR_PLAYER_FIRSTNAME_SHORT = "First name too short";
    public static final String ERROR_PLAYER_FIRSTNAME_LONG = "First name too long";
    public static final String ERROR_PLAYER_FIRSTNAME_INVALID = "Invalid first name (non-alphanumeric)";
    public static final String ERROR_PLAYER_LASTNAME_REQUIRED = "Last name required";
    public static final String ERROR_PLAYER_LASTNAME_SHORT = "Last name too short";
    public static final String ERROR_PLAYER_LASTNAME_LONG = "Last name too long";
    public static final String ERROR_PLAYER_LASTNAME_INVALID = "Invalid last name (non-alphanumeric)";
    public static final String ERROR_PLAYER_EMAIL_REQUIRED = "Email required";
    public static final String ERROR_PLAYER_EMAIL_LONG = "Email too long";
    public static final String ERROR_PLAYER_EMAIL_DUPLICATE = "Email already registered";
    public static final String ERROR_PLAYER_EMAIL_INVALID = "Invalid email address";
    public static final String ERROR_PLAYER_DOB_REQUIRED = "Date of birth required";
    public static final String ERROR_PLAYER_DOB_YOUNG = "Too young (<6 years)";

    private ValidationHandler() {}

    public static boolean validateControl(Control control, Label errorLabel, Response validation) {
        // Workaround for javafx not updating the node's style classes
        // after .remove()
        // http://stackoverflow.com/questions/10887525/javafx-style-class-wont-refresh
        if (showError(errorLabel, validation)) {
            control.getStyleClass().remove("validation-error");
            control.getStyleClass().add("validation-clear");
            return true;
        } else {
            control.getStyleClass().add("validation-error");
            control.getStyleClass().remove("validation-clear");
            return false;
        }
    }

    public static boolean showError(Label errorLabel, Response validation) {
        if (validation.success) {
            errorLabel.setVisible(false);
            return true;
        } else {
            errorLabel.setText(validation.msg);
            errorLabel.setVisible(true);
            return false;
        }
    }

    // Validation filters
    // Auth fields
    public static Response validateUsername(String username) {
        if(username == null || username.isEmpty()) {
            return new Response(false, ERROR_AUTH_USERNAME_REQUIRED);
        } else if (!username.matches("[a-zA-Z0-9]+")) {
            return new Response(false, ERROR_AUTH_USERNAME_INVALID);
        } else if (username.length() <= 3) {
            return new Response(false, ERROR_AUTH_USERNAME_SHORT);
        } else if (username.length() > 25) {
            return new Response(false, ERROR_AUTH_USERNAME_LONG);
        }
        return new Response(true);
    }

    public static Response validatePassword(String password) {
        if(password == null || password.isEmpty()) {
            return new Response(false, ERROR_AUTH_PASSWORD_REQUIRED);
        } else if (!password.matches("[a-zA-Z0-9]+")) {
            return new Response(false, ERROR_AUTH_PASSWORD_INVALID);
        } else if (password.length() <= 3) {
            return new Response(false, ERROR_AUTH_PASSWORD_SHORT);
        } else if (password.length() > 25) {
            return new Response(false, ERROR_AUTH_PASSWORD_LONG);
        }
        return new Response(true);
    }

    public static Response validateRepeatPassword(String password, String repeatPassword) {
        Response response = validatePassword(repeatPassword);

        if (!response.success) {
            return response;
        } else if(!password.equals(repeatPassword)) {
            return new Response(false, ERROR_AUTH_PASSWORD_DIFFERENT);
        }
        return new Response(true);
    }

    // Tournament fields
    public static Response validateTournamentName(String name) {
        if(name == null || name.isEmpty()) {
            return new Response(false, ERROR_TOURNAMENT_NAME_REQUIRED);
        } else if (!name.matches("[a-zA-Z0-9 ]+")) {
            return new Response(false, ERROR_TOURNAMENT_NAME_INVALID);
        } else if (name.length() <= 3) {
            return new Response(false, ERROR_TOURNAMENT_NAME_SHORT);
        } else if (name.length() > 25) {
            return new Response(false, ERROR_TOURNAMENT_NAME_LONG);
        }
        return new Response(true);
    }

    public static Response validateTournamentDBOperation(int returnValue) {
        if(returnValue == 1) {
            return new Response(true);
        } else if (returnValue == -1) {
            return new Response(false, ERROR_TOURNAMENT_NAME_DUPLICATE);
        }

        return new Response(false, ValidationHandler.ERROR_DB_CONNECTION);
    }

    // Team fields
    public static Response validateTeamName(String name) {
        if(name == null || name.isEmpty()) {
            return new Response(false, ERROR_TEAM_NAME_REQUIRED);
        } else if (!name.matches("[a-zA-Z0-9 ]+")) {
            return new Response(false, ERROR_TEAM_NAME_INVALID);
        } else if (name.length() <= 3) {
            return new Response(false, ERROR_TEAM_NAME_SHORT);
        } else if (name.length() > 25) {
            return new Response(false, ERROR_TEAM_NAME_LONG);
        }
        return new Response(true);
    }

    public static Response validateTeamPlayer(Player player) {
        if(player == null) {
            return new Response(false, ERROR_TEAM_PLAYER_REQUIRED);
        }
        return new Response(true);
    }

    public static Response validateTeamDBOperation(int returnValue) {
        if(returnValue == 1) {
            return new Response(true);
        } else if (returnValue == -1) {
            return new Response(false, ERROR_TEAM_NAME_DUPLICATE);
        }

        return new Response(false, ValidationHandler.ERROR_DB_CONNECTION);
    }


    // Player fields
    public static Response validatePlayerFirstName(String firstName) {
        if(firstName == null || firstName.isEmpty()) {
            return new Response(false, ERROR_PLAYER_FIRSTNAME_REQUIRED);
        } else if (!firstName.chars().allMatch(Character::isLetter)) {
            return new Response(false, ERROR_PLAYER_FIRSTNAME_INVALID);
        } else if (firstName.length() <= 1) {
            return new Response(false, ERROR_PLAYER_FIRSTNAME_SHORT);
        } else if (firstName.length() > 25) {
            return new Response(false, ERROR_PLAYER_FIRSTNAME_LONG);
        }
        return new Response(true);
    }

    public static Response validatePlayerLastName(String lastName) {
        if(lastName == null || lastName.isEmpty()) {
            return new Response(false, ERROR_PLAYER_LASTNAME_REQUIRED);
        } else if (!lastName.chars().allMatch(Character::isLetter)) {
            return new Response(false, ERROR_PLAYER_LASTNAME_INVALID);
        } else if (lastName.length() <= 1) {
            return new Response(false, ERROR_PLAYER_LASTNAME_SHORT);
        } else if (lastName.length() > 25) {
            return new Response(false, ERROR_PLAYER_LASTNAME_LONG);
        }
        return new Response(true);
    }

    public static Response validatePlayerEmail(String email) {
        if(email == null || email.isEmpty()) {
            return new Response(false, ERROR_PLAYER_EMAIL_REQUIRED);
        } else if (!email.matches("^(.+)@(.+)$")) {
            return new Response(false, ERROR_PLAYER_EMAIL_INVALID);
        } else if (email.length() > 25) {
            return new Response(false, ERROR_PLAYER_EMAIL_LONG);
        }
        return new Response(true);
    }

    public static Response validatePlayerDateOfBirth(LocalDate date) {
        if(date == null) {
            return new Response(false, ERROR_PLAYER_DOB_REQUIRED);
        } else if (YEARS.between(date, LocalDate.now()) < 6) {
            return new Response(false, ERROR_PLAYER_DOB_YOUNG);
        }
        return new Response(true);
    }

    public static Response validatePlayerDBOperation(int returnValue) {
        if(returnValue == 1) {
            return new Response(true);
        } else if (returnValue == -1) {
            return new Response(false, ERROR_PLAYER_EMAIL_DUPLICATE);
        }

        return new Response(false, ValidationHandler.ERROR_DB_CONNECTION);
    }
}
