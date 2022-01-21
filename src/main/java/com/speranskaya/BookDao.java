package com.speranskaya;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

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


    public Collection<Book> getAll() throws SQLException {
        Collection<Book> books = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery("SELECT * FROM book");
            while (cursor.next()) {
                books.add(createBookFromCursorIfPossible(cursor));

            }
        }
        return books;
    }

    public Optional<Book> getByID(int id) throws SQLException {

        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery(
                    String.format("SELECT * FROM book WHERE id = %d", id));
            if (cursor.next()) {
                return Optional.of(createBookFromCursorIfPossible(cursor));
            } else {
                return Optional.empty();
            }
        }
    }
    public Collection<Book> findByTitle(String text) throws SQLException {
        Collection<Book> books = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery(
                    String.format("SELECT * FROM book WHERE title LIKE '%%%s%%'", text));
            while (cursor.next()) {
                books.add(createBookFromCursorIfPossible(cursor));

            }
        }
        return books;
    }
    private Book createBookFromCursorIfPossible(ResultSet cursor) throws SQLException {
        Book book = new Book();
        book.id = cursor.getInt("id");
        book.title = cursor.getString("title");
        book.authorID = cursor.getInt("authorID");
        return book;
    }

    public void update(Book book) throws SQLException {
        if (book.id == 0) {
            throw new IllegalArgumentException("ID is not set");
        }
        final String sql = "UPDATE book SET title = ?, authorID = ?" +
                " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.title);
            statement.setInt(2, book.authorID);
            statement.setInt(3, book.id);

            statement.executeUpdate();
        }
    }

    public void insert(Book book) throws SQLException {
        if (book.id != 0) {
            throw new IllegalArgumentException("ID is :" + book.id);
        }
        if (book.authorID == 0) {
            throw new IllegalArgumentException("Book ID is not set.");
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
    public void deleteBook(int id) throws SQLException {
        if (id == 0) {
            throw new IllegalArgumentException("ID is not set");
        }
        final String sql = "DELETE FROM book WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        }
    }

    private void deleteBookTable() throws SQLException {
        try (Statement statement = connection.createStatement();) {

            statement.executeUpdate("DROP TABLE book");
        }
    }


}
