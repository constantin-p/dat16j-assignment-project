package assignment.core;

import assignment.db.MockDBProvider;
import assignment.model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class TeamSelectorController extends ModalBaseController {

    private MockDBProvider mockDB;

    @FXML
    private Button selectorCreateButton;

    @FXML
    private TableView<Team> selectorTableView;

    public TeamSelectorController(ModalDispatcher modalDispatcher, Stage stage) {
        super(modalDispatcher, stage);
        this.mockDB = new MockDBProvider();
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
        selectorTableView.setItems(mockDB.getTeams());
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
            mockDB.addTeam(selectedTeam);
        }
    }
}
