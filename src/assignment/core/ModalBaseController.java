package assignment.core;

import assignment.model.Team;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ModalBaseController implements ModalController {

    protected ModalDispatcher modalDispatcher;
    protected Stage stage;

    private boolean isOKClicked = false;
    private BooleanProperty isDisabled = new SimpleBooleanProperty(false);

    @FXML
    private Button submitButton;

    public ModalBaseController(ModalDispatcher modalDispatcher, Stage stage) {
        this.modalDispatcher = modalDispatcher;
        this.stage = stage;
    }

    @FXML
    protected void initialize() {
        submitButton.disableProperty().bind(isDisabled);
    }

    @FXML
    public void handleCancelAction(ActionEvent event) {
        stage.close();
    }

    @FXML
    public void handleOKAction(ActionEvent event) {
        setOKClicked(true);
        stage.close();
    }

    public Object result() {
        return null;
    }


    public boolean isOKClicked() {
        return isOKClicked;
    }

    public void setOKClicked(boolean OKClicked) {
        isOKClicked = OKClicked;
    }

    public boolean isDisabled() {
        return isDisabled.get();
    }

    public BooleanProperty isDisabledProperty() {
        return isDisabled;
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled.set(isDisabled);
    }
}
