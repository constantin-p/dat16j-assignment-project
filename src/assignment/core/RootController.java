package assignment.core;

import assignment.db.Database;
import assignment.model.Player;
import assignment.model.Team;
import assignment.model.Tournament;
import assignment.util.AuthManager;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RootController {

    private List<Tournament> tournaments = new ArrayList<Tournament>();

    private AuthManager authManager;
    protected ModalDispatcher modalDispatcher;

    @FXML
    private TabPane tabPane;

    @FXML
    private MenuItem usernameMenuItem;

    @FXML
    private Label statusLabel;

    public RootController() { }

    @FXML
    private void initialize() {

        statusLabel.setText("Create a tournament from File > New tournament");
        statusLabel.visibleProperty().bind(Bindings.isEmpty(tabPane.getTabs()));

        showContentUI();
    }


    public void initData(AuthManager authManager, Stage primaryStage) {
        this.authManager = authManager;
        this.modalDispatcher = new ModalDispatcher(primaryStage, () -> showContentUI());

        if (this.authManager.currentUser == null) {
            authManager.initLoginLayout.run();
            return;
        }

        usernameMenuItem.setText(this.authManager.currentUser.getUsername());
    }

    private void showContentUI() {
        int lastSelectedTournament = tabPane.getSelectionModel().getSelectedIndex();

        tabPane.getTabs().clear();
        tournaments = Tournament.dbGetAll();
        tournaments.forEach(tournament -> {

            Tab tab = new Tab();
            tab.textProperty().bind(tournament.nameProperty());
            tab.setContent(loadTabContent(tournament));

            tabPane.getTabs().add(tab);
        });

        if (lastSelectedTournament < tournaments.size()) {
            tabPane.getSelectionModel().select(lastSelectedTournament);
        }
    }


    private Node loadTabContent(Tournament tournament) {
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/tournament.fxml"));
           Parent layout = loader.load();

           TournamentController controller = loader.<TournamentController>getController();
           controller.initData(this, tournament);
           return layout;

       } catch (IOException ex) {
           ex.printStackTrace();
           return null;
       }
   }


    protected void removeTournament(Tournament tournament) {
        tabPane.getTabs().remove(tournaments.indexOf(tournament));
        tournaments.remove(tournament);
    }


    // FXML Action handlers
    // TODO: Disable edit/delete menu options while modals are open
    @FXML
    protected void handleNewTournamentAction(ActionEvent event){
        String name = "Tournament " + (tournaments.size() + 1);

        while (Tournament.dbInsert(name) == 0) {
            name+= "1";
        }

        Tournament tournament = Tournament.dbGetByName(name);
        if (tournament != null) {
            tournaments.add(tournament);

            Tab tab = new Tab();
            tab.textProperty().bind(tournament.nameProperty());
            tab.setContent(loadTabContent(tournament));

            tabPane.getTabs().add(tab);
        }
    }

    @FXML
    protected void handleSignOutAction(ActionEvent event){
        authManager.signOut();
    }


    @FXML
    public void handleNewTeamAction(ActionEvent event) {
        this.modalDispatcher.showCreateTeamModal();
    }

    @FXML
    public void handleNewPlayerAction(ActionEvent event) {
        this.modalDispatcher.showCreatePlayerModal();
    }


    @FXML
    public void handleEditTeamAction(ActionEvent event) {
        Team selectedTeam = this.modalDispatcher.showSelectTeamModal();
        if (selectedTeam != null) {
            this.modalDispatcher.showEditTeamModal(null, selectedTeam);
        }
    }

    @FXML
    public void handleEditPlayerAction(ActionEvent event) {
        Player selectedPlayer = this.modalDispatcher.showSelectPlayerModal();
        if (selectedPlayer != null) {
            this.modalDispatcher.showEditPlayerModal(null, selectedPlayer);
        }
    }
}
