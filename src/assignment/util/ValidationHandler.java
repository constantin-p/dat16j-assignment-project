package assignment.util;

import assignment.util.Response;
import assignment.model.Player;
import javafx.scene.control.Control;
import javafx.scene.control.Label;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.YEARS;

public class ValidationHandler {

    // Error messages
    public static final String ERROR_DB_CONNECTION = "db connection error | x";

    public static final String ERROR_AUTH_INVALID = "wrong credentials | x";
    public static final String ERROR_AUTH_USERNAME_REQUIRED = "required username | x";
    public static final String ERROR_AUTH_USERNAME_SHORT = "short username | x";
    public static final String ERROR_AUTH_USERNAME_LONG = "long username | x";
    public static final String ERROR_AUTH_USERNAME_INVALID = "wrong chars username | x";
    public static final String ERROR_AUTH_USERNAME_NONEXISTENT = "nonexistent username | x";
    public static final String ERROR_AUTH_USERNAME_DUPLICATE = "not unique username | x";
    public static final String ERROR_AUTH_PASSWORD_REQUIRED = "required pass | x";
    public static final String ERROR_AUTH_PASSWORD_SHORT = "short pass | x";
    public static final String ERROR_AUTH_PASSWORD_LONG = "long pass | x";
    public static final String ERROR_AUTH_PASSWORD_INVALID = "wrong chars pass | x";
    public static final String ERROR_AUTH_PASSWORD_DIFFERENT = "different pass rpass | x";

    public static final String ERROR_TOURNAMENT_NAME_REQUIRED = "required name | x";
    public static final String ERROR_TOURNAMENT_NAME_SHORT = "short name | x";
    public static final String ERROR_TOURNAMENT_NAME_LONG = "long name | x";
    public static final String ERROR_TOURNAMENT_NAME_INVALID = "wrong chars name | x";

    public static final String ERROR_TEAM_NAME_REQUIRED = "required name | x";
    public static final String ERROR_TEAM_NAME_SHORT = "short name | x";
    public static final String ERROR_TEAM_NAME_LONG = "long name | x";
    public static final String ERROR_TEAM_NAME_INVALID = "wrong chars name | x";
    public static final String ERROR_TEAM_PLAYER_REQUIRED = "required player | x";


    public static final String ERROR_PLAYER_FIRSTNAME_REQUIRED = "required firstName | x";
    public static final String ERROR_PLAYER_FIRSTNAME_SHORT = "short firstName | x";
    public static final String ERROR_PLAYER_FIRSTNAME_LONG = "long firstName | x";
    public static final String ERROR_PLAYER_FIRSTNAME_INVALID = "wrong chars firstName | x";
    public static final String ERROR_PLAYER_LASTNAME_REQUIRED = "required lastName | x";
    public static final String ERROR_PLAYER_LASTNAME_SHORT = "short lastName | x";
    public static final String ERROR_PLAYER_LASTNAME_LONG = "long lastName | x";
    public static final String ERROR_PLAYER_LASTNAME_INVALID = "wrong chars lastName | x";
    public static final String ERROR_PLAYER_EMAIL_REQUIRED = "required email | x";
    public static final String ERROR_PLAYER_EMAIL_LONG = "long email | x";
    public static final String ERROR_PLAYER_EMAIL_INVALID = "invalid email | x";
    public static final String ERROR_PLAYER_DOB_REQUIRED = "required date of birth | x";
    public static final String ERROR_PLAYER_DOB_YOUNG = "young date of birth | x";

    private ValidationHandler() {}

    public static boolean validateControl(Control control, Label errorLabel, Response validation) {
        if (showError(errorLabel, validation)) {
            control.getStyleClass().remove("validation-error");
            return true;
        } else {
            control.getStyleClass().add("validation-error");
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
}
