package jm.task.core.jdbc.util;
import java.sql.*;
import java.util.logging.*;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DB_Driver = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/mySQLtest";
    private static final String USER = "root";
    private static final String PASSWORD = "kachalka3";
    private final static Logger LOGGER = Logger.getLogger(Util.class.getName());

    public Util() {
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Class.forName(DB_Driver);
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.INFO, e.getMessage());
        }
        return connection;
    }

}
