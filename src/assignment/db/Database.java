package assignment.db;

import assignment.util.Config;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    private static Config config = Config.getInstance();
    private static Connection connection = null;

    private static String url = "jdbc:mysql://" +
            config.getProperty("DB_HOST") + ":" +
            config.getProperty("DB_PORT") + "/" +
            config.getProperty("DB_NAME");


    public static TableHandler getTable(String tableName) throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,
                    config.getProperty("DB_USER"),
                    config.getProperty("DB_PASS"));

            return new TableHandler(tableName, connection);

        } catch (Exception throwable) {
            throw throwable;
        }
    }
}