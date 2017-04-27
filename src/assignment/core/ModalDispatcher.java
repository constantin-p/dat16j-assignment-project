package assignment.core;

import assignment.model.Player;
import assignment.model.Team;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ModalDispatcher {

    private interface ControllerCreator {
        ModalController create(Stage modalStage);
    }

    private Stage primaryStage;

    public ModalDispatcher(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private Object showModal(Stage stage, ControllerCreator controllerCreator, String templatePath, String title) {
        Stage parentStage = (stage != null)
                ? stage
                : this.primaryStage;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(templatePath));

            // Create the modal Stage
            Stage modalStage = new Stage();
            modalStage.setTitle(title);
            modalStage.initModality(Modality.WINDOW_MODAL);
            modalStage.initOwner(parentStage);

            ModalController controller = controllerCreator.create(modalStage);
            loader.setController(controller);

            Parent layout = loader.load();
            modalStage.setScene(new Scene(layout));

            // Show the modal, wait until the user closes it
            // and then return the result
            modalStage.showAndWait();

            return controller.result();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Team modals: select, create & edit
    protected Team showSelectTeamModal(Stage stage) {
        return (Team) this.showModal(stage, (Stage modalStage) -> {
               return new TeamSelectorController(this, modalStage);
            }, "../view/selector.fxml", "Select a team");
    }

    protected Team showCreateTeamModal(Stage stage) {
        return (Team) this.showModal(stage, (Stage modalStage) -> {
                    return new TeamFormController(this, modalStage, new Team());
                }, "../view/team-form.fxml", "Create a team");
    }

    protected Team showEditTeamModal(Stage stage, Team team) {
        return (Team) this.showModal(stage, (Stage modalStage) -> {
                    return new TeamFormController(this, modalStage, team);
                }, "../view/team-form.fxml", "Edit");
    }

    // Player modals: select, create & edit
    protected Player showSelectPlayerModal(Stage stage) {
        return (Player) this.showModal(stage, (Stage modalStage) -> {
            return new PlayerSelectorController(this, modalStage);
        }, "../view/selector.fxml", "Select a player");
    }

    protected Player showCreatePlayerModal(Stage stage) {
        return (Player) this.showModal(stage, (Stage modalStage) -> {
            return new PlayerFormController(this, modalStage, new Player());
        }, "../view/player-form.fxml", "Create a player");
    }

    protected Player showEditPlayerModal(Stage stage, Player player) {
        return (Player) this.showModal(stage, (Stage modalStage) -> {
            return new PlayerFormController(this, modalStage, player);
        }, "../view/player-form.fxml", "Edit");
    }
}
