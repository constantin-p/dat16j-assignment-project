package assignment.core;

import assignment.model.Tournament;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RootController {

    // App state
    // TODO: bindable
    private List<Tournament> tournaments = new ArrayList<Tournament>();


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



   private Node loadTabContent(Tournament tournament) {
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/tournament.fxml"));
           Parent layout = loader.load();

           TournamentController controller = loader.<TournamentController>getController();
           controller.initData(tournament);
           return layout;

       } catch (IOException ex) {
           ex.printStackTrace();
           return null;
       }
   }

}
