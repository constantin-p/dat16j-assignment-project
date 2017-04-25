package assignment.core;

import assignment.model.Team;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ModalController {
    private RootController rootCtrl;
    private Stage stage;

    private boolean isOKClicked = false;
    private BooleanProperty isDisabled = new SimpleBooleanProperty(false);

    @FXML
    private Button submitButton;

    public ModalController(RootController rootCtrl, Stage stage) {
        this.rootCtrl = rootCtrl;
        this.stage = stage;
    }

    @FXML
    protected void initialize() {
        submitButton.disableProperty().bind(isDisabled);
    }

    public RootController getRootCtrl() {
        return rootCtrl;
    }

    public void setRootCtrl(RootController rootCtrl) {
        this.rootCtrl = rootCtrl;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
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

    protected Team result() {
        return null;
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

}
