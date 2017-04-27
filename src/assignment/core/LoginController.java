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

public class LoginController {

    private AuthManager authManager;
    private Runnable initRegisterLayout;
    private Runnable initRootLayout;

    @FXML
    private Label loginErrorLabel;

    @FXML
    private TextField loginUsernameTextField;
    private BooleanProperty isLoginUsernameValid = new SimpleBooleanProperty(false);

    @FXML
    private TextField loginPasswordTextField;
    private BooleanProperty isLoginPasswordValid = new SimpleBooleanProperty(false);

    @FXML
    private Button submitButton;

    public LoginController() {}

    @FXML
    private void initialize() {
        submitButton.disableProperty().bind(
                isLoginUsernameValid.not().or(
                        isLoginPasswordValid.not()
                )
        );

        loginUsernameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isLoginUsernameValid.set(ValidationHandler.validateControl(loginUsernameTextField,
                    loginErrorLabel, ValidationHandler.validateUsername(newValue)));
        });

        loginPasswordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isLoginPasswordValid.set(ValidationHandler.validateControl(loginPasswordTextField,
                    loginErrorLabel, ValidationHandler.validatePassword(newValue)));
        });
    }

    public void initData(AuthManager authManager, Runnable initRegisterLayout, Runnable initRootLayout) {
        this.authManager = authManager;
        this.initRegisterLayout = initRegisterLayout;
        this.initRootLayout = initRootLayout;
    }

    // FXML Action handlers
    @FXML
    public void handleLoginAction(ActionEvent event){
        Response validation = authManager.login(loginUsernameTextField.getText(),
                loginPasswordTextField.getText());

        if (ValidationHandler.showError(loginErrorLabel, validation)) {
            initRootLayout.run();
        }
    }

    @FXML
    public void handleShowRegisterAction(ActionEvent event){
        initRegisterLayout.run();
    }
}
