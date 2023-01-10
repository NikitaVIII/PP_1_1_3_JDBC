package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS users(" +
                    "user_id INT PRIMARY KEY  AUTO_INCREMENT," +
                    "name VARCHAR(30)," +
                    "lastName VARCHAR(30)," +
                    "age TINYINT);").execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try(Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            connection.prepareStatement("DROP TABLE IF EXISTS users").execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = Util.getConnection();
        try (connection) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, lastName, age)" +
                    "VALUES (?,?,?)");
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            connection.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException sq) {
                sq.printStackTrace();
            }
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        Connection connection = Util.getConnection();
        try(connection) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users " +
                    "WHERE user_id=?");
            statement.setLong(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException sq) {
                sq.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        Connection connection = Util.getConnection();
        try(connection) {
            connection.setAutoCommit(false);
            ResultSet resultSet = connection.prepareStatement("SELECT * FROM users;").executeQuery();
            connection.commit();

            List<User> list = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User(resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4));
                user.setId(resultSet.getLong(1));
                list.add(user);
            }
            return list;

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException sq) {
                sq.printStackTrace();
            }
            e.printStackTrace();
        }
        return null;
    }

    public void cleanUsersTable() {
        Connection connection = Util.getConnection();
        try(connection) {
            connection.setAutoCommit(false);
            connection.prepareStatement("DELETE FROM users;").execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException sq) {
                sq.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
