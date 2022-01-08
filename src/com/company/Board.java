package com.company;

public class Board {
    int rows, cols, movesToWin;
    char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public Board(int rows, int cols, int movesToWin) {
        this.rows = rows;
        this.cols = cols;
        this.movesToWin = movesToWin;
    }

    public void writeMove(boolean isEmpty, Player player) {
        if (isEmpty) {
            System.out.print("[ ]");
        } else {
            System.out.print("[" + player.getSign() + "]");
        }
    }

    public void writeSchema(int[][] schema, Player[] players) {
        for (int i = 0; i < this.rows + 1; i++) {
            if (i == 0) {
                for (int j = 0; j < this.cols + 1; j++) {
                    if (j == 0) {
                        System.out.print("[\\]");
                    } else {
                        System.out.print("[" + alphabet[j - 1] + "]");
                    }
                }
            } else {
                System.out.print("[" + i + "]");

                for (int j = 0; j < this.cols; j++) {
                    int status = schema[i - 1][j];
                    writeMove(status == 0, players[status]);
                }
            }

            System.out.println();
        }
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.cols;
    }
}
