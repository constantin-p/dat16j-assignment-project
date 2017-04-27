package assignment.core;

import assignment.model.Player;
import assignment.model.Team;
import assignment.util.ValidationHandler;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import assignment.util.Response;

public class TeamFormController extends ModalBaseController {

    private Team team;

    @FXML
    private Label teamErrorLabel;

    @FXML
    private TextField teamNameTextField;
    private BooleanProperty isTeamNameValid = new SimpleBooleanProperty(false);

    @FXML
    private Button teamSelectPlayerAButton;
    private BooleanProperty isTeamPlayerAValid = new SimpleBooleanProperty(false);

    @FXML
    private Button teamSelectPlayerBButton;
    private BooleanProperty isTeamPlayerBValid = new SimpleBooleanProperty(false);

    public TeamFormController(ModalDispatcher modalDispatcher, Stage stage, Team team) {
        super(modalDispatcher, stage);
        this.team = team;
    }

    @Override
    protected void initialize() {
        super.initialize();

        super.isDisabledProperty().bind(
                isTeamNameValid.not().or(
                        isTeamPlayerAValid.not().or(
                                isTeamPlayerBValid.not()
                        )
                )
        );

        teamNameTextField.textProperty().bindBidirectional(team.nameProperty());
        teamNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isTeamNameValid.set(ValidationHandler.validateControl(teamNameTextField,
                    teamErrorLabel, ValidationHandler.validateTeamName(newValue)));

        });
    }

    @Override
    public Team result() {
        if (super.isOKClicked() && !super.isDisabled()) {
            return this.team;
        }
        return null;
    }

    // FXML Action handlers
    @FXML
    public void handleSelectPlayerAAction(ActionEvent event){
        Player selectedPlayer = super.modalDispatcher.showSelectPlayerModal(super.stage);
        Response validation = ValidationHandler.validateTeamPlayer(selectedPlayer);

        if (validation.success) {
            team.setPlayerA(selectedPlayer);
            teamSelectPlayerAButton.setText(selectedPlayer.getFirstName() + " " + selectedPlayer.getLastName());
        } else {
            teamSelectPlayerAButton.setText("Select player");
        }

        isTeamPlayerAValid.set(ValidationHandler.validateControl(teamSelectPlayerAButton,
                teamErrorLabel, validation));
    }

    @FXML
    public void handleSelectPlayerBAction(ActionEvent event){
        // TODO: blacklist existing players
        Player selectedPlayer = super.modalDispatcher.showSelectPlayerModal(super.stage);
        Response validation = ValidationHandler.validateTeamPlayer(selectedPlayer);

        if (validation.success) {
            team.setPlayerB(selectedPlayer);
            teamSelectPlayerBButton.setText(selectedPlayer.getFirstName() + " " + selectedPlayer.getLastName());
        } else {
            teamSelectPlayerBButton.setText("Select player");
        }

        isTeamPlayerBValid.set(ValidationHandler.validateControl(teamSelectPlayerBButton,
                teamErrorLabel, validation));
    }
}
