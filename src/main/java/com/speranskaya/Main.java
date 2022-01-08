package com.speranskaya;

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
            int userInput = Integer.parseInt(scanner.next());
            switch (userInput) {
                case 1:
                    System.out.println("Privet, horoshego dnya.");
                    break;
                case 2:
                    System.out.println("Precrasnaya pogoda,pravda?");
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
                    System.out.println("Mozhno vzdremnut'");
                    return;
                default:
                    System.out.println("Neraspoznannaya comanda");
                    break;

            }
        }

    }
}
