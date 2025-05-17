package jm.task.core.hibernate.dao;

import jm.task.core.hibernate.model.User;
import jm.task.core.hibernate.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {

    private static final String CREATE_USERS_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS users (
                id bigint PRIMARY KEY AUTO_INCREMENT,
                name varchar(50),
                lastname varchar(50),
                age tinyint
            );
            """;
    private static final String DROP_USERS_TABLE_SQL = """
            DROP TABLE IF EXISTS users;
            """;
    private static final String SAVE_USERS_SQL = """
            INSERT INTO users(name, lastname, age)
            VALUES (?,?,?);
            """;
    private static final String REMOVE_USERS_BY_ID_SQL = """
            DELETE FROM users
            WHERE id = ?;
            """;
    private static final String GET_ALL_USERS_SQL = """
            SELECT * FROM users;
            """;
    private static final String CLEAN_USERS_TABLE_SQL = """
            TRUNCATE TABLE users;
            """;
    private static final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = Util.open().createStatement()) {
            statement.executeUpdate(CREATE_USERS_TABLE_SQL);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при создании таблицы users: ", e);
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.open().createStatement()) {
            statement.executeUpdate(DROP_USERS_TABLE_SQL);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при удалении таблицы users: ", e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = Util.open().prepareStatement(SAVE_USERS_SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при сохранении пользователя: ", e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = Util.open().prepareStatement(REMOVE_USERS_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при удалении пользователя по id: ", e);
        }
    }

    public List<User> getAllUsers() {
        try (Statement statement = Util.open().createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL_USERS_SQL);
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(buildUser(resultSet));
            }
            return users;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при получении всех пользователей: ", e);
            return List.of();
        }
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.open().createStatement()) {
            statement.executeUpdate(CLEAN_USERS_TABLE_SQL);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при очистке таблицы users: ", e);
        }
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getString("name"),
                resultSet.getString("lastname"),
                resultSet.getByte("age"));
    }

}
