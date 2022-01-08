package com.speranskaya;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new Main().run();

    }

    private void run() {
        doTask1();

    }

    private void doTask1() {
        System.out.println("Vvedite nomer comandy ");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Выберите из следующих действий : ");
            System.out.println("\t1 - создать таблицу student.");
            System.out.println("\t2 - удалить таблицу student.");
            System.out.println("\t9 - выход.");

            System.out.println("Сделайте свой выбор.");

            int userInput = Integer.parseInt(scanner.next());
            switch (userInput) {
                case 1:
                    createStudentsTable();
                    System.out.println("Таблица создана.");
                    break;
                case 2:
                    deleteStudentsTable();
                    System.out.println("Таблица удалена.");
                    break;
                case 3:
                    System.out.println("Mmmm otlichnye duhi.");
                    break;
                case 4:
                    System.out.println("Ne zabud' zont");
                    break;
                case 5:
                    System.out.println(" horoshego dnya");
                    break;
                case 6:
                    System.out.println("Pora prochest' nescol'ko stranic");
                    break;
                case 7:
                    System.out.println("Podkrepis'");
                    break;
                case 8:
                    System.out.println("Poehali na more)");
                    break;
                case 9:
                    System.out.println("Пока");
                    return;
                default:
                    System.out.println("Neraspoznannaya comanda");
                    break;

            }
        }

    }

    private void createStudentsTable() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db")) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE student(id INTEGER, name VARCHAR(100)," +
                    "age INTEGER, gender BOOL)");

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    private void deleteStudentsTable() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db")) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE student");

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

//    private void insertStudents(){
//        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db")) {
//            Statement statement = connection.createStatement();
//            statement.executeUpdate("INSERT TABLE student VALUES");
//
//        } catch (SQLException e) {
//            e.printStackTrace(System.out);
//        }
//
//    }
}
