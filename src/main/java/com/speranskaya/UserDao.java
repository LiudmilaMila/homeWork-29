package com.speranskaya;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class UserDao {
    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public void createTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS user (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nickname VARCHAR(100))");
        }
    }

    public Collection<User> getAll() throws SQLException {
        Collection<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery("SELECT * FROM user");
            while (cursor.next()) {
                users.add(createUserFromCursorIfPossible(cursor));
            }
        }
        return users;

    }

    public Optional<User> getByID(int id) throws SQLException {

        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery(
                    String.format("SELECT * FROM user WHERE id = %d", id));
            if (cursor.next()) {
                return Optional.of(createUserFromCursorIfPossible(cursor));
            } else {
                return Optional.empty();
            }
        }
    }

    private User createUserFromCursorIfPossible(ResultSet cursor) throws SQLException {
        User user = new User();
        user.id = cursor.getInt("id");
        user.nickname = cursor.getString("nickname");
        return user;
    }

    public void upDate(User user) throws SQLException {
        if (user.id == 0) {
            throw new IllegalArgumentException("ID is not set");
        }

        final String sql = "UPDATE user SET nickname = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.nickname);
            ps.setInt(2, user.id);

            ps.executeUpdate();
        }
    }

    public void insertUser(User user) throws SQLException {
        if (user.id != 0) {
            throw new IllegalArgumentException("ID is :" + user.id);
        }

        final String sql = "INSERT INTO user (nickname) VALUES(?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.nickname);

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }

    public void deleteUserByID(int id) throws SQLException {
        if (id == 0) {
            throw new IllegalArgumentException("ID is not set");
        }
        final String sql = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        }
    }

    public void deleteUserTable() throws SQLException {
        try (Statement statement = connection.createStatement();) {

            statement.executeUpdate("DROP TABLE user");
        }
    }

    public void truncateUserTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE user");
        }
    }

    public Collection<User> findByName(String text) throws SQLException {
        Collection<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery(
                    String.format("SELECT * FROM user WHERE name LIKE '%%%s%%'", text));
            while (cursor.next()) {
                users.add(createUserFromCursorIfPossible(cursor));
            }
        }
        return users;
    }
}
