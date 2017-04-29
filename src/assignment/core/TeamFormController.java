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

import java.util.ArrayList;
import java.util.List;

public class TeamFormController extends ModalBaseController {

    private Team team;
    private List<String> playerBlacklist = new ArrayList<>();
    private boolean create;

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

    public TeamFormController(ModalDispatcher modalDispatcher, Stage stage,
                              List<String> playerBlacklist, Team team, boolean create) {
        super(modalDispatcher, stage);
        this.team = team;
        this.playerBlacklist = playerBlacklist;
        this.create = create;
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
    public void handleOKAction(ActionEvent event) {
        if (create) {
            int returnValue = Team.dbInsert(team);
            if (returnValue == 1) {
                team = Team.dbGetByName(team.getName());
                super.handleOKAction(event);
            } else if (returnValue == -1) {
                ValidationHandler.validateControl(teamNameTextField,
                        teamErrorLabel, new Response(false, ValidationHandler.ERROR_TEAM_NAME_DUPLICATE));
            } else {
                ValidationHandler.validateControl(teamNameTextField,
                        teamErrorLabel, new Response(false, ValidationHandler.ERROR_DB_CONNECTION));
            }
        } else {
            super.handleOKAction(event);
        }
    }

    @Override
    public Team result() {
        if (super.isOKClicked() && !super.isDisabled()) {
            return this.team;
        }
        return null;
    }

    private List<String> computePlayerABlacklist() {
        List<String> blacklist = new ArrayList<>(playerBlacklist);

        if (team.getPlayerB() != null && team.getPlayerB().getId() != null) {
            blacklist.add(team.getPlayerB().getId());
        }

        return blacklist;
    }

    private List<String> computePlayerBBlacklist() {
        List<String> blacklist = new ArrayList<>(playerBlacklist);
        if (team.getPlayerA() != null && team.getPlayerA().getId() != null) {
            blacklist.add(team.getPlayerA().getId());
        }

        return blacklist;
    }

    // FXML Action handlers
    @FXML
    public void handleSelectPlayerAAction(ActionEvent event){
        Player selectedPlayer = super.modalDispatcher.showSelectPlayerModal(super.stage, computePlayerABlacklist());
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
        Player selectedPlayer = super.modalDispatcher.showSelectPlayerModal(super.stage, computePlayerBBlacklist());
        Response validation = ValidationHandler.validateTeamPlayer(selectedPlayer);

        System.out.println(" SELECT PLAYER B"+selectedPlayer);
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
