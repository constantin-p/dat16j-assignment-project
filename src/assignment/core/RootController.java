package assignment.core;

import assignment.Main;
import assignment.model.Tournament;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RootController {

    // App state
    // TODO: bindable
    private List<Tournament> tournaments = new ArrayList<Tournament>();

    private Main app;

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
            tabPane.getTabs().add(new Tab(tournament.getName(), loadTabContent(tournament)));
        }
    }


    public void initData(Main app) {
        this.app = app;
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


    protected void showTeamSelector() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/selector.fxml"));

            TeamSelectorController controller = new TeamSelectorController();
            loader.setController(controller);

            Parent layout = loader.load();


            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Select a team");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(app.primaryStage);
            dialogStage.setScene(new Scene(layout));

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
   protected void handleNewTournamentAction(ActionEvent event){
       Tournament createdTournament = new Tournament("Unnamed Tournament " + (tournaments.size() + 1));
       tournaments.add(createdTournament);

       tabPane.getTabs().add(new Tab(createdTournament.getName(), loadTabContent(createdTournament)));
   }

    protected void removeTournament(Tournament tournament) {
        tabPane.getTabs().remove(tournaments.indexOf(tournament));
        tournaments.remove(tournament);
    }


}
