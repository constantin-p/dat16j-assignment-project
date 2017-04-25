package assignment.core;

import assignment.db.MockDBProvider;
import assignment.model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

// TODO: Extend modal class [resolve, constructor]
public class TeamSelectorController {

    private MockDBProvider mockDB;
    private RootController rootCtrl;
    private Stage stage;

    private boolean isOKClicked = false;

    @FXML
    private Button selectorCreateButton;

    @FXML
    private ListView<Team> selectorListView;

    public TeamSelectorController(RootController rootCtrl, Stage stage) {
        this.rootCtrl = rootCtrl;
        this.stage = stage;
        this.mockDB = new MockDBProvider();
    }

    @FXML
    private void initialize() {


        selectorCreateButton.setText("New team");

        selectorListView.setItems(mockDB.getTeams());
    }

    protected Team result() {
        Team selectedTeam = selectorListView.getSelectionModel().getSelectedItem();
        if (isOKClicked && selectedTeam != null) {
            return selectedTeam;
        }
        return null;
    }

    @FXML
    public void handleCancelAction(ActionEvent event) {
        stage.close();
    }

    @FXML
    public void handleOKAction(ActionEvent event) {
        isOKClicked = true;
        stage.close();
    }

    @FXML
    public void handleCreateAction(ActionEvent event){
        Team selectedTeam = this.rootCtrl.showTeamForm(stage);
        if (selectedTeam != null) {
            mockDB.getTeams().add(selectedTeam);
        }
    }
}
