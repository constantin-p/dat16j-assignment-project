package assignment.core;


import assignment.model.Tournament;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class TournamentController {

    @FXML
    private TextField nameTextField;

    private Tournament tournament;

    public TournamentController() { }

    @FXML
    private void initialize() {

    }

    public void initData(Tournament tournament) {
        this.tournament = tournament;

        nameTextField.setText(this.tournament.getName());
    }
}
