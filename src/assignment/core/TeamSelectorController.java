package assignment.core;

import assignment.db.MockDBProvider;
import assignment.model.Player;
import assignment.model.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.List;

public class TeamSelectorController extends ModalBaseController {

    public ObservableList<Team> teams = FXCollections.observableArrayList();

    @FXML
    private Button selectorCreateButton;

    @FXML
    private TableView<Team> selectorTableView;

    public TeamSelectorController(ModalDispatcher modalDispatcher, Stage stage) {
        super(modalDispatcher, stage);
    }

    @Override
    protected void initialize() {
        super.initialize();

        selectorCreateButton.setText("New team");

        TableColumn<Team, String> nameColumn = new TableColumn("Name");
        TableColumn playersColumn = new TableColumn("Players");
        TableColumn<Team, String> playerAColumn = new TableColumn("A");
        TableColumn<Team, String> playerBColumn = new TableColumn("B");


        selectorTableView.getColumns().addAll(nameColumn, playersColumn);
        playersColumn.getColumns().addAll(playerAColumn, playerBColumn);

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        playerAColumn.setCellValueFactory(cellData -> cellData.getValue().getPlayerA().firstNameProperty());
        playerBColumn.setCellValueFactory(cellData -> cellData.getValue().getPlayerB().firstNameProperty());
        selectorTableView.setItems(teams);

        teams.clear();
        teams.setAll(Team.dbGetAll());
    }

    @Override
    public Team result() {
        Team selectedTeam = selectorTableView.getSelectionModel().getSelectedItem();
        if (super.isOKClicked() && selectedTeam != null) {
            return selectedTeam;
        }
        return null;
    }


    // FXML Action handlers
    @FXML
    public void handleCreateAction(ActionEvent event){
        Team selectedTeam = super.modalDispatcher.showCreateTeamModal(super.stage);
        if (selectedTeam != null) {
            teams.clear();
            teams.setAll(Team.dbGetAll());
        }
    }
}
