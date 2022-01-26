package com.speranskaya;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LibraryMain {
    private static final String SQLITE_CONNECTION_STRING = "jdbc:sqlite:sample.db";

    public static void main(String[] args) {
        new LibraryMain().run();
    }

    private void run() {
        try (Connection connection =
                     DriverManager.getConnection(SQLITE_CONNECTION_STRING)) {
            doTasks(connection);

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    private void doTasks(Connection connection) throws SQLException {
        ILibraryRepository repository = initializeLibrary(connection);
    }

    private ILibraryRepository initializeLibrary(Connection connection) throws SQLException {
        BookDao bookDao = new BookDao(connection);
        AuthorDao authorDao = new AuthorDao(connection);
        MessageDao messageDao = new MessageDao(connection);
        UserDao userDao = new UserDao(connection);

        bookDao.createTable();
        authorDao.createTable();
        messageDao.createTable();
        userDao.createTable();

        return  new SqlLibraryRepository(bookDao,authorDao,messageDao,userDao);
    }
}
