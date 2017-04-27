package assignment.core;

import assignment.Main;
import assignment.model.Tournament;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RootController {

    private List<Tournament> tournaments = new ArrayList<Tournament>();

    private Main app;
    protected ModalDispatcher modalDispatcher;

    @FXML
    private TabPane tabPane;

    public RootController() {
        tournaments.add(new Tournament("1"));
        tournaments.add(new Tournament("2"));
        tournaments.add(new Tournament("3"));
        tournaments.add(new Tournament("4"));
    }

    @FXML
    private void initialize() {
        for (Tournament tournament : tournaments) {
            Tab tab = new Tab();
            tab.textProperty().bind(tournament.nameProperty());
            tab.setContent(loadTabContent(tournament));

            tabPane.getTabs().add(tab);
        }
    }


    public void initData(Main app) {
        this.app = app;
        this.modalDispatcher = new ModalDispatcher(app.primaryStage);
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
        Tournament createdTournament = new Tournament("Unnamed Tournament " + (tournaments.size() + 1));
        tournaments.add(createdTournament);

        tabPane.getTabs().add(new Tab(createdTournament.getName(), loadTabContent(createdTournament)));
    }
}
