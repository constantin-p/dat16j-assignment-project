package assignment.model;

import assignment.db.Database;
import assignment.db.Storable;
import assignment.util.Auth;
import assignment.util.Response;
import assignment.util.ValidationHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Tournament implements Storable {

    private String id;
    private StringProperty name;
    public ObservableList<Team> teams = FXCollections.observableArrayList();

    public Tournament (String id, String name) {
        this.id = id;
        this.name = new SimpleStringProperty(name);

        if (this.id != null) {
            teams.setAll(Tournament.dbGetAllTeams(this.id));
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        if (Tournament.dbInsertTeam(getId(), team.getId()) == 1) {
            teams.setAll(Tournament.dbGetAllTeams(this.id));
        }
    }


    /*
     *  DB integration
     */
    @Override
    public HashMap<String, String> deconstruct() {
        HashMap<String, String> values = new HashMap<String, String>();

        values.put("name", this.name.getValue());

        return values;
    }

    public static Tournament construct(HashMap<String, String> valuesMap) {
        String id = valuesMap.get("id");
        String name = valuesMap.get("name");

        return new Tournament(id, name);
    }

    /*
     *  DB helpers
     */
    public static List<Tournament> dbGetAll() {
        List<Tournament> result = new ArrayList<>();

        try {
            List<HashMap<String, String>> returnList = Database.getTable("tournaments")
                    .getAll(Arrays.asList("id", "name"),
                            new HashMap<>(), new HashMap<>());

            returnList.forEach((HashMap<String, String> valuesMap) -> {
                result.add(Tournament.construct(valuesMap));
            });
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    public static List<Team> dbGetAllTeams(String id) {
        List<Team> result = new ArrayList<>();

        HashMap<String, String> searchQuery = new HashMap<>();
        searchQuery.put("tournament_id", id);

        try {
            List<HashMap<String, String>> returnList = Database.getTable("tournament_team")
                    .getAll(Arrays.asList("tournament_id", "team_id"),
                            new HashMap<>(), new HashMap<>());

            returnList.forEach((HashMap<String, String> valuesMap) -> {
                result.add(Team.dbGet(valuesMap.get("team_id")));
            });
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    public static Tournament dbGetByName(String name) {
        HashMap<String, String> searchQuery = new HashMap<>();
        searchQuery.put("name", name);

        try {
            HashMap<String, String> returnValues = Database.getTable("tournaments")
                    .get(Arrays.asList("id", "name"), searchQuery, new HashMap<>());

            if (returnValues.get("name").equals(name)) {
                return Tournament.construct(returnValues);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int dbInsert(String name) {
        try {
            return Database.getTable("tournaments")
                .insert(new Tournament(null, name).deconstruct());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int dbInsertTeam(String tournamentId, String teamId) {
        HashMap<String, String> entry = new HashMap<>();
        entry.put("tournament_id", tournamentId);
        entry.put("team_id", teamId);

        try {
            return Database.getTable("tournament_team")
                    .insert(entry);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int dbUpdate(String id, String name) {
        HashMap<String, String> entry = new HashMap<>();
        entry.put("name", name);

        HashMap<String, String> whitelist = new HashMap<>();
        whitelist.put("id", id);

        try {
            return Database.getTable("tournaments")
                    .update(entry, whitelist, new HashMap<>());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int dbDelete(String id) {
        HashMap<String, String> whitelist = new HashMap<>();
        whitelist.put("id", id);

        try {
            return Database.getTable("tournaments")
                    .delete(whitelist, new HashMap<>());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // For debugging
    @Override
    public String toString() {
        return this.getClass().getCanonicalName()
                + "[id: " + this.id
                + ", name: " + this.name.getValue()
                + ", teams: " + this.teams + "]";
    }
}
