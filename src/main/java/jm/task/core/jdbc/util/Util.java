package jm.task.core.jdbc.util;
import java.sql.*;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DB_Driver = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/mySQLtest";
    private static final String USER = "root";
    private static final String PASSWORD = "kachalka3";

    public Util() {
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Class.forName(DB_Driver);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Connection error");
        }
        return connection;
    }
}
