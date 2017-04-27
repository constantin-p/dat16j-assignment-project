package assignment.core;

import assignment.Main;
import assignment.db.Database;
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

        for (Tournament tournament : tournaments) {
            Tab tab = new Tab();
            tab.textProperty().bind(tournament.nameProperty());
            tab.setContent(loadTabContent(tournament));

            tabPane.getTabs().add(tab);
        }
    }


    public void initData(AuthManager authManager, Stage primaryStage) {
        this.authManager = authManager;
        this.modalDispatcher = new ModalDispatcher(primaryStage);

        if (this.authManager.currentUser == null) {
            authManager.initLoginLayout.run();
            return;
        }

        usernameMenuItem.setText(this.authManager.currentUser.getUsername());
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

    protected void updateTournamentName(Tournament tournament) {
        tabPane.getTabs().remove(tournaments.indexOf(tournament));
        tournaments.remove(tournament);
    }

    // FXML Action handlers
    // TODO: Disable edit/delete menu options while modals are open
    @FXML
    protected void handleNewTournamentAction(ActionEvent event){
        Tournament createdTournament = new Tournament("Tournament " + (tournaments.size() + 1));
        tournaments.add(createdTournament);

        tabPane.getTabs().add(new Tab(createdTournament.getName(), loadTabContent(createdTournament)));
    }

    @FXML
    protected void handleSignOutAction(ActionEvent event){
        authManager.signOut();
    }
}
