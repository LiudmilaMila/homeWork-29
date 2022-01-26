package com.speranskaya;

import java.sql.SQLException;
import java.util.Collection;

public class SqlLibraryRepository implements ILibraryRepository {
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final MessageDao messageDao;
    private final UserDao userDao;

    public SqlLibraryRepository(BookDao bookDao, AuthorDao authorDao,
                                MessageDao messageDao, UserDao userDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.messageDao = messageDao;
        this.userDao = userDao;
    }

    @Override
    public Collection<Book> getAllBooks() {
        try {
            return bookDao.getAll();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch books", e);
        }
    }

    @Override
    public Collection<Author> getAllAuthors() {
        try {
            return authorDao.getAll();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch authors", e);
        }
    }

    @Override
    public Collection<User> getAllUsers() {
        try {
            return userDao.getAll();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch users", e);
        }
    }

    @Override
    public Collection<Message> getAllMessages() {
        try {
            return messageDao.getAll();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch messages", e);
        }
    }

    @Override
    public void saveAuthor(Author author) {
        try {
            if (author.id == 0) {
                authorDao.insert(author);
            } else {
                authorDao.update(author);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save author", e);
        }
    }

    @Override
    public void saveBook(Book book, Author author) {
        try {
            if (author.id == 0) {
                authorDao.insert(author);
            }
            book.authorID = author.id;
            bookDao.insert(book);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save book", e);
        }
    }

    @Override
    public void saveUser(User user) {
        try {
            if (user.id == 0) {
                userDao.insertUser(user);
            } else {
                userDao.upDate(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save user", e);
        }
    }

    @Override
    public void saveMessage(Message message, User user, Book book) {
        try {
            if (user.id == 0) {
                userDao.insertUser(user);
            }
            if (book.id == 0) {
                bookDao.insert(book);
            }
            message.userId = user.id;
            message.bookId = book.id;
            messageDao.insertMessage(message);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save message", e);
        }
    }

    @Override
    public Collection<Book> findBooksByAuthorName(String name) {
        try {
            return bookDao.findBooksByAuthorName(name);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find books", e);
        }
    }

    @Override
    public Collection<Message> findMessagesByUserID(int id) {
        try {
            return messageDao.findMessagesByUserID(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find messages", e);
        }
    }

    @Override
    public Collection<Message> findMessagesByBookId(int id) {
        try {
            return messageDao.findMessagesByBookId(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find messages", e);
        }
    }

    @Override
    public Collection<Message> findMessagesByUserNickname(String nick) {
        try {
            return messageDao.findMessagesByUserNickname(nick);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find messages", e);
        }
    }
}
