package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    private final static String CREATE_USERS_TABLE = "CREATE TABLE USER (Id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(25), last_name VARCHAR(25), age INT)";
    private final static String SAVE_USER = "INSERT INTO USER (name, last_name, age) VALUES (?, ?, ?)";
    private final static String DELETE_ALL_USER = "TRUNCATE mySQLtest.USER";
    private final static String REMOVE_USER_BY_ID = "DELETE FROM USER WHERE id = ?";
    private final static String GET_ALL_USERS = "SELECT * FROM USER";
    private final static String DROP_TABLE = "DROP TABLE IF EXISTS USER";
    private static long ID = 1;



    public UserDaoJDBCImpl() {
    }


    public void createUsersTable() {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USERS_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void dropUsersTable() {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        User user = new User();
        user.setId(ID++);
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setByte(3, user.getAge());
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + user.getName() + " добавлен в базу данных!");

        } catch (SQLException e) {
            System.out.println(e.getMessage() + " " + e.getErrorCode());
        }
    }

    public void removeUserById(long id) {
        try ( Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {

        List<User> listUsers = new ArrayList<>();
        try (Connection connection = getConnection(); Statement statement = connection.createStatement() ) {

            ResultSet resultSet = statement.executeQuery(GET_ALL_USERS);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                listUsers.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listUsers;
    }

    public void cleanUsersTable() {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL_USER)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "Table is not found");
        }

    }
}
