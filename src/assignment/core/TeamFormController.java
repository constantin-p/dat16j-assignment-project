package assignment.core;

import assignment.model.Team;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class TeamFormController extends ModalController {

    private Team team;

    @FXML
    private TextField teamNameTextField;

    @FXML
    private Label teamErrorLabel;

    public TeamFormController(RootController rootCtrl, Stage stage, Team team) {
        super(rootCtrl, stage);
        this.team = team;
    }

    @Override
    protected void initialize() {
        super.initialize();
        teamNameTextField.setText(team.getName());

        // TODO: Validation
        teamNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!teamNameTextField.getText().matches("[a-zA-Z0-9 ]+")) {
                teamErrorLabel.setText("Invalid team name");
                teamErrorLabel.setVisible(true);
                super.setIsDisabled(true);
            } else {
                teamErrorLabel.setVisible(false);
                super.setIsDisabled(false);
            }
        });
    }

    @Override
    protected Team result() {
        if (super.isOKClicked() && !super.isDisabled()) {
            this.team.setName(this.teamNameTextField.getText());
            return this.team;
        }
        return null;
    }
}
