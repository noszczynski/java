package com.company;

import java.util.Random;
import java.util.Scanner;

public class ComputerPlayer extends Player {
    ComputerPlayer(int id, String name, char sign) {
        super(id, name, sign);
    }

    public Move getMove(int[][] moves, int columns, int rows) {
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();

        boolean correct = false;
        int row = 0, column = 0;

        while (!correct) {
            row = rand.nextInt(rows);
            column = rand.nextInt(rows);

            System.out.println("Debug Computer, move: " + moves[row][column] + "row: " + row + " column: " + column);

            if (moves[row][column] == 0) {
                correct = true;
            }
        }

        System.out.println("Get computer move (" + column + ":" + row + ")");
        return new Move(column, row);
    }
}
