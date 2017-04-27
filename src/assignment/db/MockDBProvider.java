package assignment.db;

import assignment.model.Player;
import assignment.model.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MockDBProvider {

    public ObservableList<Team> teams = FXCollections.observableArrayList();
    public ObservableList<Player> players = FXCollections.observableArrayList();


    public MockDBProvider() {}

    public ObservableList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ObservableList<Team> teams) {
        this.teams = teams;
    }

    public ObservableList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ObservableList<Player> players) {
        this.players = players;
    }


    public void addTeam(Team team) {
        getTeams().add(team);
    }

    public void addPlayer(Player player) {
        getPlayers().add(player);
    }
}
