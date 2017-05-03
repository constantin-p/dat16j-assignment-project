package assignment.core;

import assignment.model.Match;
import assignment.widgets.NumberTextField;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/*
 * TableCell's API from:
 *
 * http://grepcode.com/file/repo1.maven.org/maven2/net.java.openjfx.backport/openjfx-78-backport/1.8.0-ea-b96.1/javafx/scene/control/Cell.java
 */
public class EditableResultCell<S, T> extends TableCell<S, T> {

    private final String DIVIDER_TEXT = ":";

    private NumberTextField goalsTeamANumberTextField;
    private NumberTextField goalsTeamBNumberTextField;
    private Label dividerLabel = new Label(DIVIDER_TEXT);

    private Button saveButton;
    private BooleanProperty isResultValid = new SimpleBooleanProperty(false);

    private HBox root;

    private Runnable refreshRequest;

    public EditableResultCell(Runnable refreshRequest) {
        super();
        this.refreshRequest = refreshRequest;
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        Match match = (this.getTableRow() == null)
            ? null
            :(Match) this.getTableRow().getItem();

        if (empty || match == null) {
            super.setEditable(false);

            setText(null);
            setGraphic(null);
        } else {
            if (match.getDate() == null) {
                if (isEditable((ObservableList<Match>) this.getTableView().getItems(), match)) {
                    super.setEditable(true);
                    startEdit();
                } else {
                    super.setEditable(false);
                    setText("TBD");
                    setGraphic(null);
                }
            } else {
                super.setEditable(false);
                setText(match.getGoalsTeamA() + " " + DIVIDER_TEXT + " " + match.getGoalsTeamB());
                setGraphic(null);
            }
        }
    }


    public static <U, V> Callback<TableColumn<U, String>, TableCell<U, String>> forTableColumn(Runnable refreshRequest) {
        return column -> new EditableResultCell<>(refreshRequest);
    }


    @Override
    public void startEdit() {
        this.getTableRow().getItem();
        Match match = (Match) this.getTableRow().getItem();
        if (!isEmpty()) {
            createEditLayout();
            setText(null);
            setGraphic(root);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setGraphic(null);
    }

    private void createEditLayout() {
        Match match = (Match) this.getTableRow().getItem();

        goalsTeamANumberTextField = new NumberTextField(match.getGoalsTeamA());
        goalsTeamBNumberTextField = new NumberTextField(match.getGoalsTeamB());

        goalsTeamANumberTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isResultValid.set(!goalsTeamANumberTextField.getText().equals(goalsTeamBNumberTextField.getText()));
        });

        goalsTeamBNumberTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isResultValid.set(!goalsTeamANumberTextField.getText().equals(goalsTeamBNumberTextField.getText()));
        });


        saveButton = new Button("✔");
        saveButton.getStyleClass().add("btn-save");
        saveButton.disableProperty().bind(isResultValid.not());

        saveButton.setOnAction(event -> {
            System.out.println(" SAVE | " +
                    goalsTeamANumberTextField.getText() + " : " + goalsTeamBNumberTextField.getText());

            Match.dbUpdate(match.getId(), goalsTeamANumberTextField.getText(), goalsTeamBNumberTextField.getText());
            refreshRequest.run();
        });


        root = new HBox(2);
        root.getStyleClass().add("editable-result");
        root.getChildren().addAll(goalsTeamANumberTextField, dividerLabel, goalsTeamBNumberTextField, saveButton);

        goalsTeamANumberTextField.selectAll();
    }

    private boolean isEditable(ObservableList<Match> matches, Match match) {
        for (int i = 0; i < matches.size(); i++) {
            Match currentMatch = matches.get(i);
            if (currentMatch.getDate() == null) {
                return match.equals(currentMatch);
            }
        }
        return false;
    }
}