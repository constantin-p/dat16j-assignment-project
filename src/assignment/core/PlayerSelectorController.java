package assignment.core;

import assignment.model.Player;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class PlayerSelectorController extends ModalBaseController {

    public ObservableList<Player> players = FXCollections.observableArrayList();
    private List<String> playerBlacklist = new ArrayList<>();

    @FXML
    private Button selectorCreateButton;

    @FXML
    private TableView<Player> selectorTableView;

    @FXML
    private TextField searchTextField;


    public PlayerSelectorController(ModalDispatcher modalDispatcher, Stage stage, List<String> playerBlacklist) {
        super(modalDispatcher, stage);

        this.playerBlacklist = playerBlacklist;
        players.setAll(Player.dbGetAll(playerBlacklist));
    }

    @Override
    protected void initialize() {
        super.initialize();

        selectorCreateButton.setText("New player");

        TableColumn<Player, String> firstNameColumn = new TableColumn("First name");
        TableColumn<Player, String> lastNameColumn = new TableColumn("Last name");
        TableColumn<Player, String> emailColumn = new TableColumn("Email");
        TableColumn<Player, String> dateOfBirthColumn = new TableColumn("Date of birth");


        selectorTableView.getColumns().addAll(firstNameColumn, lastNameColumn,
                emailColumn, dateOfBirthColumn);


        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        dateOfBirthColumn.setCellValueFactory(cellData -> cellData.getValue().dateOfBirthProperty().asString());

        selectorTableView.setTableMenuButtonVisible(true);
        emailColumn.setVisible(false);
        dateOfBirthColumn.setVisible(false);

        // Setup search
        setupSearchFunctionality();

        super.isDisabledProperty()
                .bind(Bindings.isNull(selectorTableView.getSelectionModel().selectedItemProperty()));
    }

    @Override
    public Player result() {
        Player selectedPlayer = selectorTableView.getSelectionModel().getSelectedItem();
        if (super.isOKClicked() && selectedPlayer != null) {
            return selectedPlayer;
        }
        return null;
    }

    private void setupSearchFunctionality() {
        FilteredList<Player> filteredData = new FilteredList<>(players, p -> true);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(player -> {
                // No search term
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (player.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (player.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (player.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (player.getDateOfBirth().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Player> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(selectorTableView.comparatorProperty());

        selectorTableView.setItems(sortedData);
    }


    // FXML Action handlers
    @FXML
    public void handleCreateAction(ActionEvent event){
        Player selectedPlayer = super.modalDispatcher.showCreatePlayerModal(super.stage);
        if (selectedPlayer != null) {
            players.setAll(Player.dbGetAll(playerBlacklist));
        }
    }

    @FXML
    public void handleClearSearchAction(ActionEvent event){
        searchTextField.setText("");
    }
}
