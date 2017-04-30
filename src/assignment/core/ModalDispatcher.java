package assignment.core;

import assignment.model.Player;
import assignment.model.Team;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModalDispatcher {

    private interface ControllerCreator {
        ModalController create(Stage modalStage);
    }

    private Stage primaryStage;
    private Runnable updateRequest;
    public BooleanProperty isOpen = new SimpleBooleanProperty(false);

    public ModalDispatcher(Stage primaryStage, Runnable updateRequest) {
        this.primaryStage = primaryStage;
        this.updateRequest = updateRequest;
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
            isOpen.set(true);
            modalStage.showAndWait();
            isOpen.set(false);

            return controller.result();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Team modals: select, create & edit
    protected Team showSelectTeamModal(Stage stage,
                   List<String> teamBlacklist, List<String> playerBlacklist) {
        return (Team) this.showModal(stage, (Stage modalStage) -> {
               return new TeamSelectorController(this, modalStage,
                       teamBlacklist, playerBlacklist);
            }, "../view/selector.fxml", "Select a team");
    }

    protected Team showSelectTeamModal() {
        return showSelectTeamModal(null, new ArrayList<>(), new ArrayList<>());
    }


    protected Team showCreateTeamModal(Stage stage, List<String> playerBlacklist) {
        return (Team) this.showModal(stage, (Stage modalStage) -> {
                    return new TeamFormController(this, modalStage,
                            playerBlacklist, new Team(), true);
                }, "../view/team-form.fxml", "Create a team");
    }

    protected Team showCreateTeamModal() {
        return showCreateTeamModal(null, new ArrayList<>());
    }


    protected Team showEditTeamModal(Stage stage, Team team) {
        return (Team) this.showModal(stage, (Stage modalStage) -> {
                    return new TeamFormController(this, modalStage,
                            new ArrayList<>(), team, false);
                }, "../view/team-form.fxml", "Edit");
    }

    // Player modals: select, create & edit
    protected Player showSelectPlayerModal(Stage stage, List<String> playerBlacklist) {
        return (Player) this.showModal(stage, (Stage modalStage) -> {
            return new PlayerSelectorController(this, modalStage,
                    playerBlacklist);
        }, "../view/selector.fxml", "Select a player");
    }

    protected Player showSelectPlayerModal() {
        return showSelectPlayerModal(null, new ArrayList<>());
    }

    protected Player showCreatePlayerModal(Stage stage) {
        return (Player) this.showModal(stage, (Stage modalStage) -> {
            return new PlayerFormController(this, modalStage, new Player(), true);
        }, "../view/player-form.fxml", "Create a player");
    }

    protected Player showCreatePlayerModal() {
        return showCreatePlayerModal(null);
    }

    protected Player showEditPlayerModal(Stage stage, Player player) {
        return (Player) this.showModal(stage, (Stage modalStage) -> {
            return new PlayerFormController(this, modalStage, player, false);
        }, "../view/player-form.fxml", "Edit");
    }

    protected void updateRootUI() {
        this.updateRequest.run();
    }
}
