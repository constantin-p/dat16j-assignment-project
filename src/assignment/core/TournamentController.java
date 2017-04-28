package assignment.core;


import assignment.model.Team;
import assignment.model.Tournament;
import assignment.util.Response;
import assignment.util.ValidationHandler;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class TournamentController {

    private Tournament tournament;
    private RootController rootCtrl;

    @FXML
    private Label infoErrorLabel;

    @FXML
    private TextField infoNameTextField;
    private BooleanProperty isTournamentNameValid = new SimpleBooleanProperty(false);
    private String infoNameSaved;

    @FXML
    private Button infoEditButton;

    @FXML
    private Button infoCancelButton;

    @FXML
    private Button infoSaveButton;


    @FXML
    private ListView teamListView;

    public TournamentController() {}

    @FXML
    private void initialize() {
        // Remove the node when invisible
        infoEditButton.managedProperty().bind(infoEditButton.visibleProperty());
        infoCancelButton.managedProperty().bind(infoCancelButton.visibleProperty());
        infoSaveButton.managedProperty().bind(infoSaveButton.visibleProperty());

        // Disable he save btn for invalid names
        infoSaveButton.disableProperty().bind(isTournamentNameValid.not());

        teamListView.setCellFactory(param -> new ListCell<Team>() {
            @Override
            protected void updateItem(Team item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
    }

    public void initData(RootController rootCtrl, Tournament tournament) {
        this.rootCtrl = rootCtrl;
        this.tournament = tournament;

        infoNameTextField.textProperty().bindBidirectional(this.tournament.nameProperty());
        infoNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {

            boolean validString = ValidationHandler.validateControl(infoNameTextField,
                    infoErrorLabel, ValidationHandler.validateTournamentName(newValue));

            Tournament foundTournament = Tournament.dbGetByName(newValue);
            if (validString &&
                    (foundTournament != null && !foundTournament.getName().equals(infoNameSaved))) {
                isTournamentNameValid.set(ValidationHandler.validateControl(infoNameTextField,
                        infoErrorLabel, new Response(false,
                                ValidationHandler.ERROR_TOURNAMENT_NAME_DUPLICATE)));
            } else {
                isTournamentNameValid.set(validString);
            }
        });

        teamListView.setItems(tournament.getTeams());
    }




    @FXML
    private void handleEditAction(ActionEvent event) {
        // Keep a copy of the current name
        infoNameSaved = infoNameTextField.getText();


        infoEditButton.setVisible(false);
        infoCancelButton.setVisible(true);
        infoSaveButton.setVisible(true);

        infoNameTextField.setDisable(false);
        infoNameTextField.requestFocus();
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        infoNameTextField.setText(infoNameSaved);

        infoCancelButton.setVisible(false);
        infoEditButton.setVisible(true);
        infoSaveButton.setVisible(false);

        infoNameTextField.setDisable(true);
    }

    @FXML
    private void handleSaveAction(ActionEvent event) {
        infoEditButton.setVisible(true);
        infoSaveButton.setVisible(false);
        infoNameTextField.setDisable(true);

        Tournament.dbUpdate(this.tournament.getId(), this.tournament.getName());
    }

    @FXML
    private void handleStartAction(ActionEvent event) {

    }

    @FXML
    private void handleDeleteAction(ActionEvent event) {
        Tournament.dbDelete(this.tournament.getId());
        rootCtrl.removeTournament(this.tournament);
    }

    @FXML
    public void handleAddAction(ActionEvent event) {
        Team selectedTeam = this.rootCtrl.modalDispatcher.showSelectTeamModal(null);
        if (selectedTeam != null) {
            tournament.addTeam(selectedTeam);
        }
    }
}
