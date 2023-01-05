package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(PreparedStatement statement = Util.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS users(" +
                "user_id INT PRIMARY KEY  AUTO_INCREMENT," +
                "name VARCHAR(30)," +
                "lastName VARCHAR(30)," +
                "age TINYINT);")) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try(PreparedStatement statement = Util.getConnection().prepareStatement("DROP TABLE IF EXISTS users")) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(PreparedStatement statement = Util.getConnection().prepareStatement("INSERT INTO users (name, lastName, age)" +
                "VALUES (?,?,?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try(PreparedStatement statement = Util.getConnection().prepareStatement("DELETE FROM users " +
                "WHERE user_id=?")) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        try(PreparedStatement statement = Util.getConnection().prepareStatement("SELECT * FROM users;")) {
            ResultSet resultSet = statement.executeQuery();
            List<User> list = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User(resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4));
                user.setId(resultSet.getLong(1));
                list.add(user);
            }
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cleanUsersTable() {
        try(PreparedStatement statement = Util.getConnection().prepareStatement("DELETE FROM users;")) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
