package assignment.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Tournament {

    private StringProperty name;
    public ObservableList<Team> teams = FXCollections.observableArrayList();

    public Tournament (String name) {
        this.name = new SimpleStringProperty(name);
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

    public ObservableList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ObservableList<Team> teams) {
        this.teams = teams;
    }



    public void addTeam(Team team) {
        this.getTeams().add(team);
    }
}
