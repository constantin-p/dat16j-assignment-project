package assignment.core;


import assignment.model.Match;
import assignment.model.Team;
import assignment.model.TeamStats;
import assignment.model.Tournament;
import assignment.util.Response;
import assignment.util.ValidationHandler;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;


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
    private Button infoStartButton;


    @FXML
    private ListView teamListView;

    @FXML
    private TableView matchesTableView;

    @FXML
    private TableView standingsTableView;

    @FXML
    private Label scheduleInfoLabel;

    @FXML
    private Label scheduleStatusLabel;

    @FXML
    private Button scheduleSaveButton;

    public TournamentController() {}

    @FXML
    private void initialize() {
        // Remove the node when invisible
        infoEditButton.managedProperty().bind(infoEditButton.visibleProperty());
        infoCancelButton.managedProperty().bind(infoCancelButton.visibleProperty());
        infoSaveButton.managedProperty().bind(infoSaveButton.visibleProperty());
//        infoStartButton.managedProperty().bind(infoSaveButton.visibleProperty());

        // Disable the save btn for invalid names
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


        // Standings table
        TableColumn<TeamStats, String> standingsPosColumn = new TableColumn("Pos");
        TableColumn<TeamStats, String> standingsTeamColumn = new TableColumn("Team");
        TableColumn<TeamStats, String> standingsPlayedColumn = new TableColumn("Played");
        TableColumn<TeamStats, String> standingsWonColumn = new TableColumn("Won");
        TableColumn<TeamStats, String> standingsLostColumn = new TableColumn("Lost");
        TableColumn<TeamStats, String> standingsGFColumn = new TableColumn("GF");
        TableColumn<TeamStats, String> standingsGAColumn = new TableColumn("GA");
        TableColumn<TeamStats, String> standingsGDColumn = new TableColumn("GD");

        standingsTableView.getColumns().addAll(standingsPosColumn, standingsTeamColumn,
                standingsPlayedColumn, standingsWonColumn, standingsLostColumn,
                standingsGFColumn, standingsGAColumn, standingsGDColumn);

        standingsPosColumn.setCellValueFactory(cellData ->
            new SimpleStringProperty(String.valueOf(1 + tournament.getStandings().indexOf(cellData.getValue()))));


        standingsTeamColumn.setCellValueFactory(cellData -> cellData.getValue().getTeam().nameProperty());
        standingsPlayedColumn.setCellValueFactory(cellData -> cellData.getValue().playedProperty().asString());
        standingsWonColumn.setCellValueFactory(cellData -> cellData.getValue().winsProperty().asString());
        standingsLostColumn.setCellValueFactory(cellData -> cellData.getValue().lossesProperty().asString());
        standingsGFColumn.setCellValueFactory(cellData -> cellData.getValue().goalsForProperty().asString());
        standingsGAColumn.setCellValueFactory(cellData -> cellData.getValue().goalsAgainstProperty().asString());
        standingsGDColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getGoalDifference())));

        // Schedule table
        TableColumn<Match, String> matchesTeamAColumn = new TableColumn("Team A");
        TableColumn<Match, String> matchesTeamBColumn = new TableColumn("Team B");
        TableColumn<Match, String> matchesResultAColumn = new TableColumn("Result");
        TableColumn<Match, String> matchesDateColumn = new TableColumn("Date");


        matchesTableView.getColumns().addAll(matchesTeamAColumn, matchesTeamBColumn,
                matchesResultAColumn, matchesDateColumn);

        matchesTeamAColumn.setCellValueFactory(cellData -> cellData.getValue().teamA.nameProperty());
        matchesTeamBColumn.setCellValueFactory(cellData -> cellData.getValue().teamB.nameProperty());

        matchesResultAColumn.setCellValueFactory((cellData) -> {
            Match currentMatch = cellData.getValue();
            if (currentMatch.getDate() != null) {
                return new SimpleStringProperty(currentMatch.getGoalsTeamA() +
                        " - " + currentMatch.getGoalsTeamB());
            } else {
                return new SimpleStringProperty("TBD");
            }
        });
        matchesDateColumn.setCellValueFactory((cellData) -> {
            Match currentMatch = cellData.getValue();
            if (currentMatch.getDate() != null) {
                return currentMatch.dateProperty().asString();
            } else {
                return new SimpleStringProperty("TBD");
            }
        });
    }

    public void initData(RootController rootCtrl, Tournament tournament) {
        this.rootCtrl = rootCtrl;
        this.tournament = tournament;

        // Disable the start button if the tournament is already running
        infoStartButton.disableProperty().bind(tournament.isRunningProperty());


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
        matchesTableView.setItems(tournament.getMatches());
        standingsTableView.setItems(tournament.getStandings());

        // Disable the start btn if the matches have been generated
//        tournament.getMatches().addListener(() -> {
//            infoSaveButton.disableProperty().bind(Bindings.isEmpty(tournament.getMatches()).not());
//        });

        scheduleInfoLabel.textProperty().bind(tournament.isRunningProperty().asString());
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
        List<Team> teams = tournament.getTeams();

        for (int i = 0; i < teams.size() - 1; i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                Team teamA = teams.get(i);
                Team teamB = teams.get(j);
                Match match = new Match(tournament.getId(),
                        teamA, teamB, 0, 0, LocalDate.now());
                if (Match.dbInsert(match) == 1) {
                    Tournament.dbInsertMatch(tournament.getId(), match.getId());
                }
            }
        }

        tournament.getMatches().setAll(Tournament.dbGetAllMatches(tournament.getId()));
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

    @FXML
    public void handleSaveResultAction(ActionEvent event) {

    }
}
