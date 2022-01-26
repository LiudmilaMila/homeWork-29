package com.speranskaya;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DaoMain {
    private static final String SQLITE_CONNECTION_STRING = "jdbc:sqlite:library.db";

    public static void main(String[] args) {
        new DaoMain().run();
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
        AuthorDao authorDao = new AuthorDao(connection);
        BookDao bookDao = new BookDao(connection);
        UserDao userDao = new UserDao(connection);
        MessageDao messageDao = new MessageDao(connection);


        System.out.println("Выберите команду ");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Выберите из следующих действий : ");

            System.out.println("\ta - создать таблицу author.");
            System.out.println("\tb - удалить таблицу author.");
            System.out.println("\tc - показать всех author.");
            System.out.println("\td - добавить автора в таблицу.");
            System.out.println("\te - удалить автора по id.");
            System.out.println("\tf - найти автора по id.");
            System.out.println("\tg - найти автора по имени.");
            System.out.println("\th - обновить автора.");
            System.out.println("\ti - очистить таблицу author.");

            System.out.println("\tj - создать таблицу book.");
            System.out.println("\tk - удалить таблицу book.");
            System.out.println("\tl - показать все book.");
            System.out.println("\tm - добавить книгу в таблицу.");
            System.out.println("\tn - удалить книгу по id.");
            System.out.println("\to - найти книгу по id.");
            System.out.println("\tp - найти книгу по названию.");
            System.out.println("\tq - обновить автора.");
            System.out.println("\tr - очистить таблицу book.");

            System.out.println("\ts - создать таблицу user.");
            System.out.println("\tst - удалить таблицу user.");
            System.out.println("\tu - показать всех поьзователей.");
            System.out.println("\tv - добавить пользователя в таблицу.");
            System.out.println("\tw - удалить пользователя по id.");
            System.out.println("\tx - обновить пользователя.");
            System.out.println("\ty - очистить таблицу user.");
            System.out.println("\tss - найти пользователя по id.");
            System.out.println("\tuu- найти пользователя по никнейму.");

            System.out.println("\taa - создать таблицу message.");
            System.out.println("\tbb - показать все отзывы.");
            System.out.println("\tcc - обновить отзыв.");
            System.out.println("\tdd - добавиь отзыв.");
            System.out.println("\tee - найти отзыв по id.");
            System.out.println("\tff - удалить отзыв по id.");
            System.out.println("\tgg- удалить таблицу message.");
            System.out.println("\thh- очистить таблицу message.");


            System.out.println("\tz - выход.");

            System.out.println("Сделайте свой выбор.");

            String userInput = scanner.next();
            switch (userInput) {
                case "a":
                    authorDao.createTable();
                    System.out.println(" Таблица author создана.");
                    break;

                case "b":
                    authorDao.deleteAuthorTable();
                    System.out.println("Таблица удалена.");
                    break;

                case "c":
                    authorDao.getAll();
                    break;

                case "d":
                    authorDao.insert(inputNewAuthor());
                    System.out.println("Автор добавлен.");
                    break;

                case "e":
                    authorDao.deleteAuthorById(inputId());
                    System.out.println("Автор удален.");
                    break;

                case "f":
                    authorDao.getByID(inputId());
                    break;

                case "g":
                    authorDao.findByName(inputText());
                    break;
                case "h":
                    int id = inputId();
                    if (authorDao.getByID(id).isPresent()) {
                        Author author = inputNewAuthor();
                        author.id = id;
                        authorDao.update(author);
                    }
                    System.out.println("Автор обновлён.");
                    break;
                case "i":
                    authorDao.truncateAuthorTable();
                    System.out.println("Таблица очищена.");
                    break;


                case "j":
                    bookDao.createTable();
                    System.out.println(" Таблица book создана.");
                    break;

                case "k":
                    bookDao.deleteBookTable();
                    System.out.println("Таблица удалена.");
                    break;
                case "l":
                    bookDao.getAll();
                    break;
                case "m":
                    bookDao.insert(inputNewBook());
                    System.out.println("Книга добавлена.");
                    break;
                case "n":
                    bookDao.deleteBook(inputId());
                    System.out.println("Книга удалена.");
                    break;
                case "o":
                    bookDao.getByID(inputId());
                    break;
                case "p":
                    bookDao.findByTitle(inputText());
                    break;
                case "q":
                    int bId = inputId();
                    if (bookDao.getByID(bId).isPresent()) {
                        Book book = inputNewBook();
                        book.id = bId;
                        bookDao.update(book);
                    }
                    System.out.println("Автор обновлён.");
                    break;
                case "r":
                    bookDao.truncateBookTable();
                    System.out.println("Таблица очищена.");
                    break;


                case "s":
                    userDao.createTable();
                    System.out.println(" Таблица user создана.");
                    break;

                case "st":
                    userDao.deleteUserTable();
                    System.out.println("Таблица удалена.");
                    break;
                case "u":
                    userDao.getAll();
                    break;
                case "v":
                    userDao.insertUser(inputNewUser());
                    System.out.println("Пользователь добавлен.");
                    break;
                case "w":
                    userDao.deleteUserByID(inputId());
                    System.out.println("Пользователь удален.");
                    break;
                case "x":
                    int uId = inputId();
                    if (userDao.getByID(uId).isPresent()) {
                        User user = inputNewUser();
                        user.id = uId;
                        userDao.upDate(user);
                    }
                    System.out.println("Пользователь обновлён.");
                    break;
                case "y":
                    userDao.truncateUserTable();
                    System.out.println("Таблица очищена.");
                    break;
                case "ss":
                    userDao.getByID(inputId());
                    break;
                case "uu":
                    userDao.findByName(inputText());
                    break;


                case "aa":
                    messageDao.createTable();
                    System.out.println("Таблица создана");
                    break;
                case "bb":
                    messageDao.getAll();
                    break;
                case "cc":
                    int mId = inputId();
                    if (messageDao.getByID(mId).isPresent()) {
                        Message message = inputNewMessage();
                        message.id = mId;
                        messageDao.upDate(message);
                    }
                    System.out.println("Отзыв обновлён.");
                    break;
                case "dd":
                    messageDao.insertMessage(inputNewMessage());
                    System.out.println("Отзыв добавлен.");
                    break;
                case "ee":
                    messageDao.getByID(inputId());
                    break;
                case "ff":
                    messageDao.deleteMessageByID(inputId());
                    System.out.println("Отзыв удален");
                    break;
                case "gg":
                    messageDao.deleteMessageTable();
                    System.out.println("Таблица удалена.");
                    break;
                case "hh":
                    messageDao.truncateMessageTable();
                    System.out.println("Таблица очищена.");
                    break;


                case "z":
                    System.out.println("Спасибо за то, что вы с нами!");
                    return;
                default:
                    System.out.println("Нераспознанная команда. Попробуйте еще раз.");
                    break;
            }
        }

    }

    private Message inputNewMessage() {
        Message message = new Message();
        Scanner scan = new Scanner(System.in);
        while (true) {

            System.out.println("Введите никнейм(не больше 500 символов)");
            String line = scan.next();
            if (line != null) {
                message.text = line;
                break;
            } else {
                System.out.println("Неверный ввод данных");
            }
        }
        return message;
    }

    private User inputNewUser() {
        User user = new User();
        Scanner scan = new Scanner(System.in);
        while (true) {

            System.out.println("Введите никнейм(не больше ста символов)");
            String line = scan.next();
            if (line != null) {
                user.nickname = line;
                break;
            } else {
                System.out.println("Неверный ввод данных");
            }
        }
        return user;
    }

    private Book inputNewBook() {
        Book book = new Book();
        Scanner scan = new Scanner(System.in);

        while (true) {

            System.out.println("Введите название(не больше ста символов)");
            String line = scan.next();
            if (line != null) {
                book.title = line;
                break;
            } else {
                System.out.println("Неверный ввод данных");
            }
        }

        while (true) {
            System.out.println("Введите ID автора(число)");
            int number2 = scan.nextInt();
            if (number2 > 0) {
                book.authorID = number2;
                break;
            } else {
                System.out.println("Неверный ввод данных");
            }
        }
        return book;

    }


    private String inputText() {
        Scanner scanner = new Scanner(System.in);
        String text;
        while (true) {
            System.out.println("Введите запрос");
            text = scanner.next();
            if (text != null) {
                break;
            } else {
                System.out.println("Неверный ввод данных");
            }
        }
        return text;
    }

    private int inputId() {
        Scanner scan = new Scanner(System.in);
        int id;
        while (true) {
            System.out.println("Введите id , которое хотите удалить(число)");
            int number = scan.nextInt();
            if (number > 0) {
                id = number;
                break;
            } else {
                System.out.println("Неверный ввод данных");
            }
        }
        return id;
    }

    private Author inputNewAuthor() {
        Author author = new Author();
        Scanner scan = new Scanner(System.in);

        while (true) {

            System.out.println("Введите фамилию (не больше ста символов)");
            String line = scan.next();
            if (line != null) {
                author.name = line;
                break;
            } else {
                System.out.println("Неверный ввод данных");
            }
        }

        while (true) {
            System.out.println("Введите год рождения(число)");
            int number2 = scan.nextInt();
            if (number2 > 0) {
                author.birthYear = number2;
                break;
            } else {
                System.out.println("Неверный ввод данных");
            }
        }
        return author;

    }
}

