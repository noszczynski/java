package com.company;

import java.util.Scanner;

public class Player {
    int id;
    char sign = '0';
    String name = "";

    public Player(int id, String name, char sign) {
        if (id == 0) {
//            throw new Exception("id cannot be 0");
        }

        this.id = id;
        this.name = name;
        this.sign = sign;
    }

    public int getId() {
        return this.id;
    }

    public char getSign() {
        return this.sign;
    }

    public String getName() {
        return this.name;
    }

    public Move getMove() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ruch gracza " + this.name + ": ");
        boolean correct = false;
        char moveColumn = 'A';
        int moveRow = 1;

        while (!correct) {
            String move = scanner.nextLine();
            moveColumn = move.charAt(0);
            moveRow = (int) move.charAt(1) - 48;

            // TODO check validation of input
            correct = true;
        }

        // TODO check if move exist and can be executed

        return new Move(moveColumn, moveRow);
    }
}
