package assignment.core;


import assignment.Main;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.beans.value.ObservableBooleanValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class LoginController {

    private final String ERROR_MESSAGE_USERNAME_SHORT = "Usernname is too short (between 6 and 25 characters)";
    private final String ERROR_MESSAGE_USERNAME_LONG = "Username is too long (between 6 and 25 characters)";
    private final String ERROR_MESSAGE_PASSWORD_SHORT = "Password is too short (between 6 and 25 characters)";
    private final String ERROR_MESSAGE_PASSWORD_LONG= "Password is too long (between 6 and 25 characters)";
    private final String ERROR_MESSAGE_INVALID = "No account with the given credentials";

    private Main app;

    @FXML
    protected TextField usernameTextField;

    @FXML
    protected PasswordField passwordPasswordField;

    @FXML
    protected Button loginButton;

    @FXML
    protected Label errorLabel;

    public LoginController() {

    }

    @FXML
    private void initialize() {
        loginButton.disableProperty().bind(
                usernameTextField.textProperty().isEmpty().or(
                        passwordPasswordField.textProperty().isEmpty()
                )
        );

        usernameTextField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event) {
                TextField username = (TextField) event.getSource();
                int length = username.getText().length();
                if (length < 6 ) {
                    errorLabel.setText(ERROR_MESSAGE_USERNAME_SHORT);
                    usernameTextField.getStyleClass().add("error");
                    errorLabel.setVisible(true);
                } else if (length > 25) {
                    errorLabel.setText(ERROR_MESSAGE_USERNAME_LONG);
                    usernameTextField.getStyleClass().add("error");
                    errorLabel.setVisible(true);
                } else {
                    errorLabel.setVisible(false);
                    usernameTextField.getStyleClass().remove("error");
                }
            }
        });

        passwordPasswordField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                PasswordField password = (PasswordField) event.getSource();
                int lenght = password.getText().length();
                if (lenght < 6) {
                    errorLabel.setText(ERROR_MESSAGE_PASSWORD_SHORT);
                    passwordPasswordField.getStyleClass().add("error");
                    errorLabel.setVisible(true);
                } else if (lenght > 25) {
                    errorLabel.setText(ERROR_MESSAGE_PASSWORD_LONG);
                    passwordPasswordField.getStyleClass().add("error");
                    errorLabel.setVisible(true);
                } else {
                    errorLabel.setVisible(false);
                    passwordPasswordField.getStyleClass().remove("error");
                }
            }
        });
    }

    public void init(Main app) {
        this.app = app;
    }

    @FXML
    protected void handleShowToRegisterAction() {
        this.app.showAuthRegisterLayout();
    }


}
