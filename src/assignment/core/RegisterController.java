package assignment.core;

import assignment.util.AuthManager;
import assignment.util.Response;
import assignment.util.ValidationHandler;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegisterController {

    private AuthManager authManager;
    private Runnable initLoginLayout;

    @FXML
    private Label registerErrorLabel;

    @FXML
    private TextField registerUsernameTextField;
    private BooleanProperty isRegisterUsernameValid = new SimpleBooleanProperty(false);

    @FXML
    private TextField registerPasswordTextField;
    private BooleanProperty isRegisterPasswordValid = new SimpleBooleanProperty(false);

    @FXML
    private TextField registerRepeatPasswordTextField;
    private BooleanProperty isRegisterRepeatPasswordValid = new SimpleBooleanProperty(false);

    @FXML
    private Button submitButton;

    public RegisterController() {}

    @FXML
    private void initialize() {
        submitButton.disableProperty().bind(
                isRegisterUsernameValid.not().or(
                        isRegisterPasswordValid.not().or(
                                isRegisterRepeatPasswordValid.not()
                        )
                )
        );

        registerUsernameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isRegisterUsernameValid.set(ValidationHandler.validateControl(registerUsernameTextField,
                    registerErrorLabel, ValidationHandler.validateUsername(newValue)));
        });

        registerPasswordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isRegisterPasswordValid.set(ValidationHandler.validateControl(registerPasswordTextField,
                    registerErrorLabel, ValidationHandler.validatePassword(newValue)));
        });

        registerRepeatPasswordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isRegisterRepeatPasswordValid.set(ValidationHandler.validateControl(registerRepeatPasswordTextField,
                    registerErrorLabel, ValidationHandler.validateRepeatPassword(registerPasswordTextField.getText(), newValue)));
        });
    }

    public void initData(AuthManager authManager, Runnable initLoginLayout) {
        this.authManager = authManager;
        this.initLoginLayout = initLoginLayout;
    }

    // FXML Action handlers
    @FXML
    public void handleRegisterAction(ActionEvent event){
        Response validation = authManager.register(registerUsernameTextField.getText(),
                registerPasswordTextField.getText());

        if (ValidationHandler.showError(registerErrorLabel, validation)) {
            initLoginLayout.run();
        }
    }

    @FXML
    public void handleShowLoginAction(ActionEvent event){
        initLoginLayout.run();
    }
}
