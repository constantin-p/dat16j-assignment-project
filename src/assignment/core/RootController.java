package assignment.core;

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

        getTournaments();
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

    private void getTournaments() {
        try {
            List<HashMap<String, String>> returnList = Database.getTable("tournaments")
                    .getAll(Arrays.asList("id", "name"),
                            new HashMap<>(), new HashMap<>());

            tabPane.getTabs().clear();
            tournaments = new ArrayList<>();
            returnList.forEach((HashMap<String, String> valuesMap) -> {
                Tournament tournament = Tournament.construct(valuesMap);

                Tab tab = new Tab();
                tab.textProperty().bind(tournament.nameProperty());
                tab.setContent(loadTabContent(tournament));

                System.out.println(" Tournament | " + tournament);
                tournaments.add(tournament);
                tabPane.getTabs().add(tab);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
