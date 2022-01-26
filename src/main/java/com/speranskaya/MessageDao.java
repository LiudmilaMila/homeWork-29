package com.speranskaya;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class MessageDao {
    private final Connection connection;

    public MessageDao(Connection connection) {
        this.connection = connection;
    }

    public void createTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS message(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "text VARCHAR(500)," +
                    "userId INTEGER," +
                    "bookId INTEGER)");
        }
    }

    public Collection<Message> getAll() throws SQLException {
        Collection<Message> messages = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery("SELECT * FROM message");
            while (cursor.next()) {
                messages.add(createMessageFromCursorIfPossible(cursor));

            }
        }
        return messages;

    }

    public Optional<Message> getByID(int id) throws SQLException {

        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery(
                    String.format("SELECT * FROM message WHERE id = %d", id));
            if (cursor.next()) {
                return Optional.of(createMessageFromCursorIfPossible(cursor));
            } else {
                return Optional.empty();
            }
        }
    }

    private Message createMessageFromCursorIfPossible(ResultSet cursor) throws SQLException {
        Message message = new Message();
        message.id = cursor.getInt("id");
        message.text = cursor.getString("text");
        message.userId = cursor.getInt("userId");
        message.bookId = cursor.getInt("bookId");
        return message;
    }

    public void upDate(Message message) throws SQLException {
        if (message.id == 0) {
            throw new IllegalArgumentException("ID is not set");
        }

        final String sql = "UPDATE message SET text = ?, userId = ?, bookId = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, message.text);
            ps.setInt(2, message.userId);
            ps.setInt(3, message.bookId);

            ps.executeUpdate();
        }
    }

    public void insertMessage(Message message) throws SQLException {
        if (message.id != 0) {
            throw new IllegalArgumentException("ID is :" + message.id);
        }
        if (message.userId == 0) {
            throw new IllegalArgumentException("User ID is not set.");
        }
        if (message.bookId == 0) {
            throw new IllegalArgumentException("Book ID is not set.");
        }

        final String sql = "INSERT INTO message (text, userId, bookId) VALUES(?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, message.text);
            ps.setInt(2, message.userId);
            ps.setInt(3, message.bookId);

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating message failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    message.id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating message failed, no ID obtained.");
                }
            }
        }
    }

    public void deleteMessageByID(int id) throws SQLException {
        if (id == 0) {
            throw new IllegalArgumentException("ID is not set");
        }
        final String sql = "DELETE FROM message WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        }
    }

    public void deleteMessageTable() throws SQLException {
        try (Statement statement = connection.createStatement();) {

            statement.executeUpdate("DROP TABLE message");
        }
    }

    public void truncateMessageTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE message");
        }
    }

    public Collection<Message> findMessagesByUserID(int id) throws SQLException {
        Collection<Message> messages = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM message +" +
                        "JOIN user ON message.userId = user.id " +
                        "WHERE user.id = ?")) {
            statement.setInt(1, id);

            ResultSet cursor = statement.executeQuery();
            while (cursor.next()) {
                messages.add(createMessageFromCursorIfPossible(cursor));
            }
            return messages;
        }
    }

    public Collection<Message> findMessagesByBookId(int id) throws SQLException {
        Collection<Message> messages = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM message " +
                        "JOIN book ON message.bookId = book.id " +
                        "WHERE book.id = ?")) {
            statement.setInt(1, id);

            ResultSet cursor = statement.executeQuery();
            while (cursor.next()) {
                messages.add(createMessageFromCursorIfPossible(cursor));
            }
            return messages;
        }
    }

    public Collection<Message> findMessagesByUserNickname(String nick) throws SQLException {
        Collection<Message> messages = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM message" +
                        " JOIN user ON message.userId = user.id " +
                        "WHERE user.nickname LIKE ? "
        )){
            statement.setString(1, "%" + nick + "%");

            ResultSet cursor = statement.executeQuery();
            while (cursor.next()) {
                messages.add(createMessageFromCursorIfPossible(cursor));
            }
        }
        return messages;
    }
}
