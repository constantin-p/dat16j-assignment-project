package assignment.db;

import assignment.model.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MockDBProvider {

    public ObservableList<Team> teams = FXCollections.observableArrayList();


    public MockDBProvider() {
        this.teams.add(new Team("team 1"));
        this.teams.add(new Team("team 2"));
        this.teams.add(new Team("team 3"));
        this.teams.add(new Team("team 4"));
        this.teams.add(new Team("team 5"));
        this.teams.add(new Team("team 6"));
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

    public void selectorAddTeam(Team team) {
        this.getTeams().add(team);
    }
}
