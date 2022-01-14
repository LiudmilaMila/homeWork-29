package com.speranskaya;

import java.sql.*;

public class BookDao {
    private final Connection connection;

    public BookDao(Connection connection) {
        this.connection = connection;
    }

    public void createTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS book (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "title VARCHAR(100), " +
                    "authorId INTEGER " +
                    ")");
        }
    }
    public void insert(Book book) throws SQLException {
        if (book.id != 0) {
            throw new IllegalArgumentException("ID is :" + book.id);
        }
        if (book.authorID == 0) {
            throw new IllegalArgumentException("Author ID is not set.");
        }
        final String sql = "INSERT INTO book (title, authorID) VALUES(?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.title);
            statement.setInt(2, book.authorID);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating book failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating book failed, no ID obtained.");
                }
            }
        }
    }
}
