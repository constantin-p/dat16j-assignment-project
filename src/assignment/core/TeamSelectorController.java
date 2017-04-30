package assignment.core;

import assignment.model.Team;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TeamSelectorController extends ModalBaseController {

    private ObservableList<Team> teams = FXCollections.observableArrayList();
    private List<String> teamBlacklist = new ArrayList<>();
    private List<String> playerBlacklist = new ArrayList<>();

    @FXML
    private Button selectorCreateButton;

    @FXML
    private TableView<Team> selectorTableView;

    @FXML
    private TextField searchTextField;

    public TeamSelectorController(ModalDispatcher modalDispatcher, Stage stage, List<String> teamBlacklist, List<String> playerBlacklist) {
        super(modalDispatcher, stage);

        this.teamBlacklist = teamBlacklist;
        this.playerBlacklist = playerBlacklist;

        populateTeamList();
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

        // Setup search
        setupSearchFunctionality();

        super.isDisabledProperty()
                .bind(Bindings.isNull(selectorTableView.getSelectionModel().selectedItemProperty()));
    }

    @Override
    public Team result() {
        Team selectedTeam = selectorTableView.getSelectionModel().getSelectedItem();
        if (super.isOKClicked() && selectedTeam != null) {
            return selectedTeam;
        }
        return null;
    }

    private void setupSearchFunctionality() {
        FilteredList<Team> filteredData = new FilteredList<>(teams, p -> true);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(team -> {
                // No search term
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (team.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (team.getPlayerA().getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (team.getPlayerB().getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Team> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(selectorTableView.comparatorProperty());

        selectorTableView.setItems(sortedData);
    }


    private void populateTeamList() {
        teams.setAll(Team.dbGetAll(teamBlacklist)
                .stream()
                .filter(team -> {
                    // Prevent teams to participate into the same tournament
                    // and share players
                    return (!playerBlacklist.contains(team.getPlayerA().getId())
                            && !playerBlacklist.contains(team.getPlayerB().getId()));
                })
                .collect(Collectors.toList()));
    }

    // FXML Action handlers
    @FXML
    public void handleCreateAction(ActionEvent event){
        Team selectedTeam = super.modalDispatcher.showCreateTeamModal(super.stage, playerBlacklist);
        if (selectedTeam != null) {
            populateTeamList();
        }
    }

    @FXML
    public void handleClearSearchAction(ActionEvent event){
        searchTextField.setText("");
    }
}
