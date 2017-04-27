package assignment.db;

import com.sun.deploy.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableHandler {

    private String tableName;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet = null;

    public TableHandler(String tableName, Connection connection) {
        this.tableName = tableName;
        this.connection = connection;
    }

    private void close() throws SQLException {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
            }

            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException throwable) {
            throw throwable;
        }
    }

    public HashMap<String, String> get(List<String> selectionColumns,
                                       HashMap<String, String> query) throws SQLException {
        List<String> queryValues = new ArrayList<>();

        for (Map.Entry<String, String> querySet : query.entrySet()) {
            queryValues.add(querySet.getKey() + "='" + querySet.getValue() + "'");
        }

        String statement = "SELECT " + StringUtils.join(selectionColumns, ",") +
                " FROM " + tableName +
                " WHERE " + StringUtils.join(queryValues, " AND ");

        try {
            preparedStatement = connection.prepareStatement(statement);
            resultSet = preparedStatement.executeQuery();

            // Convert the first row of the result into a hash map
            HashMap<String, String> result = new HashMap<>();
            if (resultSet.next()) {
                for (String column : selectionColumns) {
                    result.put(column, resultSet.getString(column));
                }
            }
            return result;
        } catch (SQLException throwable) {
            throw throwable;
        } finally {
            close();
        }
    }

    public int insert(HashMap<String, String> rowValuesMap) throws SQLException {
        List<String> values = new ArrayList<>();

        String keys = "";
        String placeholderValues = "";
        for (Map.Entry<String, String> querySet : rowValuesMap.entrySet()) {
            keys += querySet.getKey() + ",";
            placeholderValues += "?,";
            values.add(querySet.getValue());
        }
        if (!keys.isEmpty() && !placeholderValues.isEmpty()) {
            keys = keys.substring(0, keys.length() - 1);
            placeholderValues = placeholderValues.substring(0, placeholderValues.length() - 1);
        }

        String statement = "INSERT INTO " + tableName + "(" + keys + ") VALUES(" + placeholderValues + ")";

        try {
            preparedStatement = connection.prepareStatement(statement);

            for (int i = 0; i < values.size(); i++) {
                preparedStatement.setString((i + 1), values.get(i));
            }

            /*
             *  From: https://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html#executeUpdate()
             *
             *  [1..] the row count for SQL Data Manipulation Language (DML) statements
             *  [0]   for SQL statements that return nothing
             */
            return preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throw throwable;
        } finally {
            close();
        }
    }
}
