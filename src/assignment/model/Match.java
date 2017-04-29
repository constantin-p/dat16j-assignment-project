package assignment.model;

import assignment.db.Database;
import assignment.db.Storable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;


public class Match implements Storable {

    private static final String DATE_FORMAT = "dd-MM-yyyy";

    private String id;
    private IntegerProperty goalsTeamA;
    private IntegerProperty goalsTeamB;

    public Team teamA;
    public Team teamB;

    private ObjectProperty<LocalDate> date;

    public Match(String tournamentId, Team teamA, Team teamB, int goalsTeamA, int goalsTeamB, LocalDate date) {
        this.id = tournamentId + ":" + teamA.getId() + ":" + teamB.getId();
        this.teamA = teamA;
        this.teamB = teamB;
        this.goalsTeamA = new SimpleIntegerProperty(goalsTeamA);
        this.goalsTeamB = new SimpleIntegerProperty(goalsTeamB);
        this.date = new SimpleObjectProperty<>(date);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getGoalsTeamA() {
        return goalsTeamA.get();
    }

    public IntegerProperty goalsTeamAProperty() {
        return goalsTeamA;
    }

    public void setGoalsTeamA(int goalsTeamA) {
        this.goalsTeamA.set(goalsTeamA);
    }

    public int getGoalsTeamB() {
        return goalsTeamB.get();
    }

    public IntegerProperty goalsTeamBProperty() {
        return goalsTeamB;
    }

    public void setGoalsTeamB(int goalsTeamB) {
        this.goalsTeamB.set(goalsTeamB);
    }

    public Team getTeamA() {
        return teamA;
    }

    public void setTeamA(Team teamA) {
        this.teamA = teamA;
    }

    public Team getTeamB() {
        return teamB;
    }

    public void setTeamB(Team teamB) {
        this.teamB = teamB;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    /*
        *  DB integration
        */
    @Override
    public HashMap<String, String> deconstruct() {
        HashMap<String, String> values = new HashMap<>();

        values.put("id", this.getId());
        values.put("goals_team_a", this.goalsTeamA.getValue().toString());
        values.put("goals_team_b", this.goalsTeamB.getValue().toString());
//        values.put("date", DateTimeFormatter.ofPattern(DATE_FORMAT)
//                .format(this.date.getValue()));

        return values;
    }

    public static Match construct(HashMap<String, String> valuesMap) {

        String id = valuesMap.get("id");
        int goals_team_a = Integer.parseInt(valuesMap.get("goals_team_a"));
        int goals_team_b = Integer.parseInt(valuesMap.get("goals_team_b"));

        String[] parts = id.split(":");
        Team teamA = Team.dbGet(parts[1]);
        Team teamB = Team.dbGet(parts[2]);

        String dateString = valuesMap.get("date");

        LocalDate date = (dateString != null)
                ? LocalDate.parse(valuesMap.get("date"), DateTimeFormatter.ofPattern(DATE_FORMAT))
                : null;

        return new Match(parts[0], teamA, teamB, goals_team_a, goals_team_b, date);
    }

    /*
     *  DB helpers
     */

    public static Match dbGet(String id) {
        HashMap<String, String> searchQuery = new HashMap<>();
        searchQuery.put("id", id);

        try {
            HashMap<String, String> returnValues = Database.getTable("matches")
                    .get(Arrays.asList("id", "goals_team_a", "goals_team_b", "date"),
                            searchQuery, new HashMap<>());

            if (returnValues.get("id") != null && returnValues.get("id").equals(id)) {
                return Match.construct(returnValues);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int dbInsert(Match match) {
        try {
            return Database.getTable("matches")
                    .insert(match.deconstruct());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int dbUpdate(String id, String goalsTeamA, String goalsTeamB) {
        HashMap<String, String> entry = new HashMap<>();
        entry.put("goals_team_a", goalsTeamA);
        entry.put("goals_team_b", goalsTeamB);
        entry.put("date", DateTimeFormatter.ofPattern(DATE_FORMAT)
                .format(LocalDate.now()));

        HashMap<String, String> whitelist = new HashMap<>();
        whitelist.put("id", id);

        try {
            return Database.getTable("matches")
                    .update(entry, whitelist, new HashMap<>());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}

