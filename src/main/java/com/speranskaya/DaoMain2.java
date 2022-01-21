package com.speranskaya;

import java.util.Scanner;

public class DaoMain2 {
    public static void main(String[] args) {
        new DaoMain2().run();
    }

    private void run() {
        doTasks();
    }

    private void doTasks() {
        System.out.println("Выберите команду ");
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("Выберите из следующих действий : ");
            System.out.println("\ta - создать таблицу author.");
            System.out.println("\tb - создать таблицу book.");
            System.out.println("\tc - создать таблицу user.");
            System.out.println("\td - создать таблицу message.");

            System.out.println("\t2 - удалить таблицу student.");
            System.out.println("\t3 - добавить студента.");
            System.out.println("\t4 - отсортировать по имени.");
            System.out.println("\t5 - найти по имени.");

            System.out.println("\t9 - выход.");

            System.out.println("Сделайте свой выбор.");
        }
    }
}

