package com.company;

import java.util.Locale;
import java.util.Scanner;

import static java.util.Locale.US;

public class Main {

    public static void writeSquareness(double a, double b, double c) {
        if (a == 0) {
            System.out.println("Funkcja liniowa");
            return;
        }

        double delta = Math.pow(b, 2) - 4 * a * c;

        if (delta < 0) {
            System.out.println("Brak miejsc zerowych");
            return;
        }

        double x1 = (-b - Math.sqrt(delta))/(2 * a);

        if (delta == 0) {
            System.out.println("Miejsce zerowe: x:" + x1);
            return;
        }

        double x2 = (-b + Math.sqrt(delta))/(2 * a);

        System.out.println("Miejsca zerowe: x1:" + x1 + ", x2:" + x2);
    }

    public static void main(String[] args) {
        // TODO setDefault to main branch
        Locale.setDefault(US);

	    System.out.println(Test.getHello());
        Scanner scanner = new Scanner(System.in);

        writeSquareness(1,5,6);

        System.out.println("Enter text:");
        String scanString = scanner.nextLine();
        System.out.println("Enter integer:");
        int scanInteger = scanner.nextInt();
        System.out.println("Enter double:");
        double scanDouble = scanner.nextDouble();

        System.out.println("-=-=-=-=-=-=-=-=-=-");

        System.out.println(scanString);
        System.out.println(scanInteger);
        System.out.println(scanDouble);

        System.out.println("-=-=-=-=-=-=-=-=-=-");

    }
}
