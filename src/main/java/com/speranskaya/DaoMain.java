package com.speranskaya;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;

public class DaoMain {
    private static final String SQLITE_CONNECTION_STRING = "jdbc:sqlite:sample.db";

    public static void main(String[] args) {
        new DaoMain().run();
    }

    private void run() {
        try (Connection connection =
                     DriverManager.getConnection(SQLITE_CONNECTION_STRING)) {
            doSqlTasks(connection);

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    private void doSqlTasks(Connection connection) throws SQLException {
        AuthorDao authorDao = new AuthorDao(connection);
        BookDao bookDao = new BookDao(connection);

        authorDao.createTable();
        bookDao.createTable();

        Collection<Author> authors = authorDao.getAll();
        System.out.println("authors: " + authors);

        if (authors.isEmpty()) {
            Author author1 = new Author("Толкин", 1900);
            Author author2 = new Author("Страуструп", 1950);

            authorDao.insert(author1);
            System.out.println("author1: " + author1);
            authorDao.insert(author2);
            System.out.println("author2: " + author2);

            authors.add(author1);
            authors.add(author2);

        }
        Author firstAuthor = authors.stream().findFirst().
                orElseThrow(RuntimeException::new);
        Book book = new Book("Властелин Колец");
        book.authorID = firstAuthor.id;

        bookDao.insert(book);

        System.out.println("book: " + book);

        authorDao.deleteAuthor(7);
        System.out.println("authors: " + authors);
    }


}
