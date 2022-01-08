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

    public Move getMove(int[][] moves, int columns, int rows) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ruch gracza " + this.name + ": ");

        boolean correct = false;
        int column = 1, row = 1;

        while (!correct) {
            String move = scanner.nextLine();
            column = (int) move.charAt(0) - 65;
            row = (int) move.charAt(1) - 49;

            System.out.println("Debug Player, move: " + moves[row][column] + "row: " + row + " column: " + column);

            if (moves[row][column] == 0) {
                correct = true;
            }
        }

        return new Move(column, row);
    }
}
