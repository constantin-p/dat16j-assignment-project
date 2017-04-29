package assignment.model;

import javafx.beans.property.*;

public class TeamStats {

    private ObjectProperty<Team> team;
    private IntegerProperty played = new SimpleIntegerProperty(0);
    private IntegerProperty wins = new SimpleIntegerProperty(0);
    private IntegerProperty losses = new SimpleIntegerProperty(0);

    private IntegerProperty goalsFor = new SimpleIntegerProperty(0);
    private IntegerProperty goalsAgainst = new SimpleIntegerProperty(0);


    public TeamStats(Team team) {
        this.team = new SimpleObjectProperty<>(team);
    }

    public Team getTeam() {
        return team.get();
    }

    public ObjectProperty<Team> teamProperty() {
        return team;
    }

    public int getPlayed() {
        return played.get();
    }

    public IntegerProperty playedProperty() {
        return played;
    }

    public int getWins() {
        return wins.get();
    }

    public IntegerProperty winsProperty() {
        return wins;
    }

    public int getLosses() {
        return losses.get();
    }

    public IntegerProperty lossesProperty() {
        return losses;
    }

    public int getGoalsFor() {
        return goalsFor.get();
    }

    public IntegerProperty goalsForProperty() {
        return goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst.get();
    }

    public IntegerProperty goalsAgainstProperty() {
        return goalsAgainst;
    }

    public int getGoalDifference() {
        return goalsFor.get() - goalsAgainst.get();
    }

    public void addWin(int GF, int GA) {
        wins.setValue(wins.getValue() + 1);
        played.setValue(played.getValue() + 1);

        goalsFor.setValue(goalsFor.getValue() + GF);
        goalsAgainst.setValue(goalsAgainst.getValue() + GA);
    }

    public void addLoss(int GF, int GA) {
        losses.setValue(losses.getValue() + 1);
        played.setValue(played.getValue() + 1);

        goalsFor.setValue(goalsFor.getValue() + GF);
        goalsAgainst.setValue(goalsAgainst.getValue() + GA);
    }

    public static int compare(TeamStats ts1, TeamStats ts2) {
        if (ts1.getWins() == ts2.getWins()) {
            if (ts1.getPlayed() == ts2.getPlayed()) {
                return ts2.getGoalDifference() - ts1.getGoalDifference();
            } else {
                return ts2.getPlayed() - ts1.getPlayed();
            }
        } else {
            return ts2.getWins() - ts1.getWins();
        }
    }
}
