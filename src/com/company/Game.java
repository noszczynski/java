package com.company;

import java.util.Scanner;

public class Game {
    Board board;
    Player[] players = {null, null}; // winner, player1, player2
    int[][] game;

    public Game(Player player1, Player player2) {
        this.players[0] = player1;
        this.players[1] = player2;
    }

    public void fillFields() {
        int[][] arr = new int[this.board.rows][this.board.cols];

        for (int i = 0; i < this.board.rows; i++) {
            for (int j = 0; j < this.board.cols; j++) {
                arr[i][j] = 0;
            }
        }

        this.game = arr;
    }

    public void getStatus() {
        for (int[] row : this.game) {
            for (int field : row) {
                System.out.print(field);
            }
            System.out.println();
        }
    }

    public void getMove(Player player) {

    }

    private int readSize() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Wielkość planszy: ");
        return scanner.nextInt();
    }

    private int readMovesToWin() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Liczba znaków obok siebie aby wygrać: ");
        return scanner.nextInt();
    }

    public void start() {
//        int size = this.readSize();
//        int movesToWin = this.readMovesToWin();
        int size = 5, movesToWin = 3;

        this.board = new Board(size, size, movesToWin);

        int[][] game = new int[size][size];
        this.game = game;

        this.board.writeSchema(game, players);


        this.fillFields();
    }
}
