package com.speranskaya;

import java.util.Collection;

public interface ILibraryRepository {
    Collection<Book> getAllBooks();

    Collection<Author> getAllAuthors();

    Collection<User> getAllUsers();

    Collection<Message> getAllMessages();


    void saveAuthor(Author author);

    void saveBook(Book book, Author author);

    void saveUser(User user);

    void saveMessage(Message message, User user, Book book);

    Collection<Book> findBooksByAuthorName(String name);

    Collection<Message> findMessagesByUserID(int id);
    Collection<Message> findMessagesByBookId(int id);
    Collection<Message> findMessagesByUserNickname(String nick);
}
