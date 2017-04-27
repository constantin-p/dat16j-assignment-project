package assignment.core;

import assignment.core.Response;
import assignment.model.Player;
import javafx.scene.control.Control;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import static java.time.temporal.ChronoUnit.YEARS;

public class ValidationHandler {

    // Error messages
    private static final String ERROR_TOURNAMENT_NAME_REQUIRED = "required name | x";
    private static final String ERROR_TOURNAMENT_NAME_SHORT = "short name | x";
    private static final String ERROR_TOURNAMENT_NAME_LONG = "long name | x";
    private static final String ERROR_TOURNAMENT_NAME_INVALID = "wrong chars firstName | x";

    private static final String ERROR_TEAM_NAME_REQUIRED = "required name | x";
    private static final String ERROR_TEAM_NAME_SHORT = "short name | x";
    private static final String ERROR_TEAM_NAME_LONG = "long name | x";
    private static final String ERROR_TEAM_NAME_INVALID = "wrong chars firstName | x";
    private static final String ERROR_TEAM_PLAYER_REQUIRED = "required player | x";


    private static final String ERROR_PLAYER_FIRSTNAME_REQUIRED = "required firstName | x";
    private static final String ERROR_PLAYER_FIRSTNAME_SHORT = "short firstName | x";
    private static final String ERROR_PLAYER_FIRSTNAME_LONG = "long firstName | x";
    private static final String ERROR_PLAYER_FIRSTNAME_INVALID = "wrong chars firstName | x";
    private static final String ERROR_PLAYER_LASTNAME_REQUIRED = "required lastName | x";
    private static final String ERROR_PLAYER_LASTNAME_SHORT = "short lastName | x";
    private static final String ERROR_PLAYER_LASTNAME_LONG = "long firstName | x";
    private static final String ERROR_PLAYER_LASTNAME_INVALID = "wrong chars firstName | x";
    private static final String ERROR_PLAYER_EMAIL_REQUIRED = "required email | x";
    private static final String ERROR_PLAYER_EMAIL_LONG = "long email | x";
    private static final String ERROR_PLAYER_EMAIL_INVALID = "invalid email | x";
    private static final String ERROR_PLAYER_DOB_REQUIRED = "required date of birth | x";
    private static final String ERROR_PLAYER_DOB_YOUNG = "young date of birth | x";

    private ValidationHandler() {}

    protected static boolean validateControl(Control control, Label errorLabel, Response validation) {
        if (validation.success) {
            errorLabel.setVisible(false);
            control.getStyleClass().remove("validation-error");
            return true;
        } else {
            errorLabel.setText(validation.msg);
            errorLabel.setVisible(true);
            control.getStyleClass().add("validation-error");
            return false;
        }
    }

    // Validation filters
    // Tournament fields
    protected static Response validateTournamentName(String name) {
        if(name == null || name.isEmpty()) {
            return new Response(false, ERROR_TOURNAMENT_NAME_REQUIRED);
        } else if (!name.matches("[a-zA-Z0-9 ]+")) {
            return new Response(false, ERROR_TOURNAMENT_NAME_INVALID);
        } else if (name.length() <= 3) {
            return new Response(false, ERROR_TOURNAMENT_NAME_SHORT);
        } else if (name.length() > 20) {
            return new Response(false, ERROR_TOURNAMENT_NAME_LONG);
        }
        return new Response(true);
    }

    // Team fields
    protected static Response validateTeamName(String name) {
        if(name == null || name.isEmpty()) {
            return new Response(false, ERROR_TEAM_NAME_REQUIRED);
        } else if (!name.matches("[a-zA-Z0-9 ]+")) {
            return new Response(false, ERROR_TEAM_NAME_INVALID);
        } else if (name.length() <= 3) {
            return new Response(false, ERROR_TEAM_NAME_SHORT);
        } else if (name.length() > 20) {
            return new Response(false, ERROR_TEAM_NAME_LONG);
        }
        return new Response(true);
    }

    protected static Response validateTeamPlayer(Player player) {
        if(player == null) {
            return new Response(false, ERROR_TEAM_PLAYER_REQUIRED);
        }
        return new Response(true);
    }

    // Player fields
    protected static Response validatePlayerFirstName(String firstName) {
        if(firstName == null || firstName.isEmpty()) {
            return new Response(false, ERROR_PLAYER_FIRSTNAME_REQUIRED);
        } else if (!firstName.chars().allMatch(Character::isLetter)) {
            return new Response(false, ERROR_PLAYER_FIRSTNAME_INVALID);
        } else if (firstName.length() <= 1) {
            return new Response(false, ERROR_PLAYER_FIRSTNAME_SHORT);
        } else if (firstName.length() > 20) {
            return new Response(false, ERROR_PLAYER_FIRSTNAME_LONG);
        }
        return new Response(true);
    }

    protected static Response validatePlayerLastName(String lastName) {
        if(lastName == null || lastName.isEmpty()) {
            return new Response(false, ERROR_PLAYER_LASTNAME_REQUIRED);
        } else if (!lastName.chars().allMatch(Character::isLetter)) {
            return new Response(false, ERROR_PLAYER_LASTNAME_INVALID);
        } else if (lastName.length() <= 1) {
            return new Response(false, ERROR_PLAYER_LASTNAME_SHORT);
        } else if (lastName.length() > 20) {
            return new Response(false, ERROR_PLAYER_LASTNAME_LONG);
        }
        return new Response(true);
    }

    protected static Response validatePlayerEmail(String email) {
        if(email == null || email.isEmpty()) {
            return new Response(false, ERROR_PLAYER_EMAIL_REQUIRED);
        } else if (!email.matches("^(.+)@(.+)$")) {
            return new Response(false, ERROR_PLAYER_EMAIL_INVALID);
        } else if (email.length() > 25) {
            return new Response(false, ERROR_PLAYER_EMAIL_LONG);
        }
        return new Response(true);
    }

    protected static Response validatePlayerDateOfBirth(LocalDate date) {
        if(date == null) {
            return new Response(false, ERROR_PLAYER_DOB_REQUIRED);
        } else if (YEARS.between(date, LocalDate.now()) < 6) {
            return new Response(false, ERROR_PLAYER_DOB_YOUNG);
        }
        return new Response(true);
    }
}
