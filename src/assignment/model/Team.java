package assignment.model;

import assignment.db.Database;
import assignment.db.Storable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Team implements Storable {

    private String id;
    private StringProperty name;
    private ObjectProperty<Player> playerA;
    private ObjectProperty<Player> playerB;

    public Team() {
        this.name = new SimpleStringProperty("");
        this.playerA = new SimpleObjectProperty(null);
        this.playerB = new SimpleObjectProperty(null);
    }

    public Team(String id, String name, Player playerA, Player playerB) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.playerA = new SimpleObjectProperty(playerA);
        this.playerB = new SimpleObjectProperty(playerB);
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





    /*
     *  DB integration
     */
    @Override
    public HashMap<String, String> deconstruct() {
        HashMap<String, String> values = new HashMap<String, String>();

        values.put("name", this.name.getValue());
        values.put("player_a_id", this.playerA.getValue().getId());
        values.put("player_b_id", this.playerB.getValue().getId());

        return values;
    }

    public static Team construct(HashMap<String, String> valuesMap) {
    System.out.println(valuesMap.get("player_a_id")  + " : " + valuesMap.get("player_b_id"));
        String id = valuesMap.get("id");
        String name = valuesMap.get("name");
        Player playerA = Player.dbGet(valuesMap.get("player_a_id"));
        Player playerB = Player.dbGet(valuesMap.get("player_b_id"));

        return new Team(id, name, playerA, playerB);
    }

    /*
    *  DB helpers
    */
    public static List<Team> dbGetAll() {
        List<Team> result = new ArrayList<>();

        try {
            List<HashMap<String, String>> returnList = Database.getTable("teams")
                    .getAll(Arrays.asList("id", "name", "player_a_id", "player_b_id"),
                            new HashMap<>(), new HashMap<>());

            returnList.forEach((HashMap<String, String> valuesMap) -> {
                result.add(Team.construct(valuesMap));
            });
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    public static Team dbGet(String id) {
        HashMap<String, String> searchQuery = new HashMap<>();
        searchQuery.put("id", id);

        try {
            HashMap<String, String> returnValues = Database.getTable("teams")
                    .get(Arrays.asList("id", "name", "player_a_id", "player_b_id"),
                            searchQuery, new HashMap<>());

            if (returnValues.get("id") != null && returnValues.get("id").equals(id)) {
                return Team.construct(returnValues);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Team dbGetByName(String name) {
        HashMap<String, String> searchQuery = new HashMap<>();
        searchQuery.put("name", name);

        try {
            HashMap<String, String> returnValues = Database.getTable("teams")
                    .get(Arrays.asList("id", "name", "player_a_id", "player_b_id"),
                            searchQuery, new HashMap<>());

            if (returnValues.get("name") != null && returnValues.get("name").equals(name)) {
                return Team.construct(returnValues);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int dbInsert(Team team) {
        try {
            return Database.getTable("teams")
                    .insert(team.deconstruct());
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
                + ", firstName: " + this.name.getValue()
                + ", playerA: " + this.playerA.getValue()
                + ", playerB: " + this.playerB.getValue() + "]";
    }
}