package assignment.core;


import assignment.model.Team;
import assignment.model.Tournament;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;


public class TournamentController {

    @FXML
    private TextField infoNameTextField;

    @FXML
    private Button infoEditButton;

    @FXML
    private Button infoSaveButton;


    @FXML
    private ListView teamListView;

    private RootController rootCtrl;
    private Tournament tournament;

    public TournamentController() { }

    @FXML
    private void initialize() {

    }

    public void initData(RootController rootCtrl, Tournament tournament) {
        this.rootCtrl = rootCtrl;
        this.tournament = tournament;

        infoNameTextField.setText(this.tournament.getName());

        teamListView.setItems(tournament.getTeams());

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




    @FXML
    private void editTournamentInfoAction(ActionEvent event)
    {

        infoEditButton.setVisible(false);
        infoSaveButton.setVisible(true);
        infoNameTextField.setDisable(false);
        infoNameTextField.requestFocus();
    }

    @FXML
    private void saveTournamentInfoAction(ActionEvent event) {
        infoEditButton.setVisible(true);
        infoSaveButton.setVisible(false);
        infoNameTextField.setDisable(true);
    }

    @FXML
    private void deleteTournamentAction(ActionEvent event) {
        rootCtrl.removeTournament(this.tournament);
    }

    @FXML
    public void addTeamAction(ActionEvent event) {
        tournament.addTeam(new Team("testTeam"));

        this.rootCtrl.showTeamSelector();
    }


}
