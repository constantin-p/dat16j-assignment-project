package assignment.db;

import assignment.util.Config;
import com.mysql.jdbc.PreparedStatement;

import java.sql.*;


public class Database {

    private Config config = Config.getInstance();

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private String url = "jdbc:mysql://" +
            config.getProperty("DB_HOST") + ":" +
            config.getProperty("DB_PORT") + "/" +
            config.getProperty("DB_NAME");


    private void connect(Runnable job) throws Exception {
        try {
            connect = DriverManager.getConnection(url,
                    config.getProperty("DB_USER"),
                    config.getProperty("DB_PASS"));



        } catch (Exception e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        }
    }
}