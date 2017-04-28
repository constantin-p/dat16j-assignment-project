package assignment.core;

import assignment.db.Database;
import assignment.util.Auth;
import assignment.util.ValidationHandler;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.ZoneId;

import assignment.model.Player;
import assignment.util.Response;

public class PlayerFormController extends ModalBaseController {

    private Player player;
    private boolean create;

    @FXML
    private Label playerErrorLabel;

    @FXML
    private TextField playerFirstNameTextField;
    private BooleanProperty isPlayerFirstNameValid = new SimpleBooleanProperty(false);

    @FXML
    private TextField playerLastNameTextField;
    private BooleanProperty isPlayerLastNameValid = new SimpleBooleanProperty(false);

    @FXML
    private TextField playerEmailTextField;
    private BooleanProperty isPlayerEmailValid = new SimpleBooleanProperty(false);

    @FXML
    private DatePicker playerDateOfBirthDatePicker;
    private BooleanProperty isPlayerDateOfBirthValid = new SimpleBooleanProperty(false);


    public PlayerFormController(ModalDispatcher modalDispatcher, Stage stage, Player player, boolean create) {
        super(modalDispatcher, stage);
        this.player = player;
        this.create = create;
    }

    @Override
    protected void initialize() {
        super.initialize();

        super.isDisabledProperty().bind(
                isPlayerFirstNameValid.not().or(
                        isPlayerLastNameValid.not().or(
                                isPlayerEmailValid.not().or(
                                        isPlayerDateOfBirthValid.not()
                                )
                        )
                )
        );


        playerFirstNameTextField.textProperty().bindBidirectional(player.firstNameProperty());
        playerFirstNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isPlayerFirstNameValid.set(ValidationHandler.validateControl(playerFirstNameTextField,
                    playerErrorLabel, ValidationHandler.validatePlayerFirstName(newValue)));

        });

        playerLastNameTextField.textProperty().bindBidirectional(player.lastNameProperty());
        playerLastNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isPlayerLastNameValid.set(ValidationHandler.validateControl(playerLastNameTextField,
                    playerErrorLabel, ValidationHandler.validatePlayerLastName(newValue)));

        });

        playerEmailTextField.textProperty().bindBidirectional(player.emailProperty());
        playerEmailTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isPlayerEmailValid.set(ValidationHandler.validateControl(playerEmailTextField,
                    playerErrorLabel, ValidationHandler.validatePlayerEmail(newValue)));

        });
    }

    @Override
    public void handleOKAction(ActionEvent event) {
        if (create) {
            if (Player.dbInsert(player) == 1) {
                player = Player.dbGetByEmail(player.getEmail());
                super.handleOKAction(event);
            }
            // Error saving the player
        } else {
            super.handleOKAction(event);
        }
    }

    @Override
    public Player result() {
        if (super.isOKClicked() && !super.isDisabled()) {
            return this.player;
        }
        return null;
    }

    private Response addPlayer() {
        try {
            int returnValue = Database.getTable("players")
                    .insert(this.player.deconstruct());

            if (returnValue == 1) {
                return new Response(true);
            }

            // Invalid response
            return new Response(false);
        } catch (MySQLIntegrityConstraintViolationException e) {
            return new Response(false, ValidationHandler.ERROR_PLAYER_EMAIL_DUPLICATE);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, e.getMessage());
        }
    }


    // FXML Action handlers
    @FXML
    public void handleSetDateOfBirthAction(ActionEvent event){
        LocalDate date = playerDateOfBirthDatePicker.getValue();
        System.out.println("Selected date: " + date + " " + date.atStartOfDay(ZoneId.of("UTC")));

        Response validation = ValidationHandler.validatePlayerDateOfBirth(date);

        if (validation.success) {
            player.setDateOfBirth(date);
        }
        isPlayerDateOfBirthValid.set(ValidationHandler.validateControl(playerDateOfBirthDatePicker,
                playerErrorLabel, validation));
    }
}
