package assignment.core;


import assignment.Main;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class RegisterController {

    private final String ERROR_MESSAGE_USERNAME_SHORT = "Usernname is too short (between 6 and 25 characters)";
    private final String ERROR_MESSAGE_USERNAME_LONG = "Username is too long (between 6 and 25 characters)";
    private final String ERROR_MESSAGE_PASSWORD_SHORT = "Password is too short (between 6 and 25 characters)";
    private final String ERROR_MESSAGE_PASSWORD_LONG = "Password is too long (between 6 and 25 characters)";
    private final String ERROR_MESSAGE_PASSWORD_UNMATCH = "Password and repeat password don't match";
    private final String ERROR_MESSAGE_INVALID = "No account with the given credentials";

    private Main app;

    @FXML
    protected TextField usernameTextField;

    @FXML
    protected PasswordField passwordPasswordField;

    @FXML
    protected PasswordField repeatpasswordPasswordField;

    @FXML
    protected Button registerButton;

    @FXML
    protected Label errorLabel;

    public RegisterController() {

    }

    @FXML
    private void initialize() {
        registerButton.disableProperty().bind(
                usernameTextField.textProperty().isEmpty().or(
                        passwordPasswordField.textProperty().isEmpty()).or(
                                repeatpasswordPasswordField.textProperty().isEmpty()
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

        repeatpasswordPasswordField.textProperty().addListener((obs,oldValue,newValue) -> {
            if (!repeatpasswordPasswordField.getText().equals(passwordPasswordField.getText())){
                errorLabel.setText(ERROR_MESSAGE_PASSWORD_UNMATCH);
                repeatpasswordPasswordField.getStyleClass().add("error");
                errorLabel.setVisible(true);
            } else {
                errorLabel.setVisible(false);
                passwordPasswordField.getStyleClass().remove("remove");
            }
        });

    }

    public void init(Main app) {
        this.app = app;
    }
    @FXML
    protected void handleShowToAuthAction() {
        this.app.showAuthLoginLayout();
    }

}
