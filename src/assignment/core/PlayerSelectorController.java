package assignment.core;

import assignment.db.Database;
import assignment.model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PlayerSelectorController extends ModalBaseController {

    public ObservableList<Player> players = FXCollections.observableArrayList();

    @FXML
    private Button selectorCreateButton;

    @FXML
    private TableView<Player> selectorTableView;


    public PlayerSelectorController(ModalDispatcher modalDispatcher, Stage stage) {
        super(modalDispatcher, stage);
//        this.mockDB = new MockDBProvider();
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

        selectorTableView.setItems(players);

        players.clear();
        players.setAll(Player.dbGetAll());
    }

    @Override
    public Player result() {
        Player selectedPlayer = selectorTableView.getSelectionModel().getSelectedItem();
        if (super.isOKClicked() && selectedPlayer != null) {
            return selectedPlayer;
        }
        return null;
    }

    // FXML Action handlers
    @FXML
    public void handleCreateAction(ActionEvent event){
        Player selectedPlayer = super.modalDispatcher.showCreatePlayerModal(super.stage);
        if (selectedPlayer != null) {
            players.clear();
            players.setAll(Player.dbGetAll());
        }
    }
}
