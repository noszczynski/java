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
        String moveInput = scanner.nextLine();

        boolean correct = false;
        char columnChar, rowChar;
        int column, row;

        columnChar = Character.toUpperCase(moveInput.charAt(0));
        rowChar = moveInput.charAt(1);
        column = ((int) columnChar) - 65;
        row = ((int) rowChar) - 49;

        Move move = new Move(column, row);

        if (moves[row][column] == 0) {
            move.setOk(true);
            return move;
        }

        move.setOk(false);
        return move;
    }
}
