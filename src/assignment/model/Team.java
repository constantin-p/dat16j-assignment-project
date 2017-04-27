package assignment.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Team {

    private StringProperty name;
    private ObjectProperty<Player> playerA;
    private ObjectProperty<Player> playerB;

    public Team() {
        this.name = new SimpleStringProperty("");
        this.playerA = new SimpleObjectProperty(null);
        this.playerB = new SimpleObjectProperty(null);
    }

    public Team(String name, Player playerA, Player playerB) {
        this.name = new SimpleStringProperty(name);
        this.playerA = new SimpleObjectProperty(playerA);
        this.playerB = new SimpleObjectProperty(playerB);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Player getPlayerA() {
        return playerA.get();
    }

    public ObjectProperty<Player> playerAProperty() {
        return playerA;
    }

    public void setPlayerA(Player playerA) {
        this.playerA.set(playerA);
    }

    public Player getPlayerB() {
        return playerB.get();
    }

    public ObjectProperty<Player> playerBProperty() {
        return playerB;
    }

    public void setPlayerB(Player playerB) {
        this.playerB.set(playerB);
    }
}