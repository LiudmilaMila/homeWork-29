package com.speranskaya;

import java.sql.*;
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
            System.out.println("\t3 - добавить студента.");
            System.out.println("\t4 - отсортировать по имени.");

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
                    Student student = inputStudent();
                    insertStudent(student);
                    System.out.println("Студент добавлен");
                    break;
                case 4:
                    sortByName();
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

    private void insertStudent(Student student) {

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db")) {
            Statement statement = connection.createStatement();
            String sql = String.format("INSERT INTO student VALUES(%d, '%s', %d, %b)"
                    , student.id,
                    student.name,
                    student.age,
                    student.gender);

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }

    }

    private Student inputStudent() {
        Student student = new Student();
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("Введите ID(число)");
            int number = scan.nextInt();
            if (number > 0) {
                student.id = number;
                break;
            } else {
                System.out.println("Неверный ввод данных");
            }
        }

        while (true) {

            System.out.println("Введите имя (не больше ста символов)");
            String line = scan.next();
            if (line != null) {
                student.name = line;
                break;
            } else {
                System.out.println("Неверный ввод данных");
            }
        }

        while (true) {
            System.out.println("Введите возраст(число)");
            int number2 = scan.nextInt();
            if (number2 > 0) {
                student.age = number2;
                break;
            } else {
                System.out.println("Неверный ввод данных");
            }
        }

        while (true) {
            System.out.println("Введите пол(true - жен/false - муж)");
            student.gender = scan.nextBoolean();
            break;
        }
        return student;
    }

    private void sortByName() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db")) {
            Statement statement = connection.createStatement();
            ResultSet cursor = statement.executeQuery("SELECT * FROM student ORDER BY name ASC");
            while (cursor.next()) {
                System.out.println("id = " + cursor.getInt("id"));
                System.out.println("name = " + cursor.getString("name"));
                System.out.println("age = " + cursor.getInt("age"));
                System.out.println("gender = " + cursor.getBoolean("gender"));

            }


        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
}
