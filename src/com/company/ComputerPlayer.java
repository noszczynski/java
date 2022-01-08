package com.company;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ComputerPlayer extends Player {
    ComputerPlayer(int id, String name, char sign) {
        super(id, name, sign);
    }

    public Move getMove(int[][] moves, int columns, int rows) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception ex) {
            //
        }
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();

        boolean correct = false;
        int row = 0, column = 0;

        row = rand.nextInt(rows);
        column = rand.nextInt(rows);
        Move move = new Move(column, row);

        if (moves[row][column] == 0) {
            move.setOk(true);
            return move;
        }

        move.setOk(false);
        return move;
    }
}
