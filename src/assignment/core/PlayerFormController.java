package assignment.core;

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


    public PlayerFormController(ModalDispatcher modalDispatcher, Stage stage, Player player) {
        super(modalDispatcher, stage);
        this.player = player;
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
    public Player result() {
        if (super.isOKClicked() && !super.isDisabled()) {
            return this.player;
        }
        return null;
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
