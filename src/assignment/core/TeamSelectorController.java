package assignment.core;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TeamSelectorController {

    @FXML
    private Button selectorCreateButton;

    @FXML
    private void initialize() {

        selectorCreateButton.setText("New team");
    }
}
