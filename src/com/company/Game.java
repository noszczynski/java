package com.company;

import java.util.Scanner;

public class Game {
    Board board;
    Player[] players = {null, null, null}; // winner, player1, player2
    int[][] moves;
    int turn = 1, movesToWin = 3;

    public Game(Player player1, Player player2) {
        this.players[player1.getId()] = player1;
        this.players[player2.getId()] = player2;
    }

    public void fillFields() {
        int[][] arr = new int[this.board.rows][this.board.cols];

        for (int i = 0; i < this.board.rows; i++) {
            for (int j = 0; j < this.board.cols; j++) {
                arr[i][j] = 0;
            }
        }

        this.moves = arr;
    }

    public void getStatus() {
        for (int[] row : this.moves) {
            for (int field : row) {
                System.out.print(field);
            }
            System.out.println();
        }
    }

    private void refresh() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void saveMove(char column, int row, Player player) {
        int actualRow = row - 1;
        int actualColumn = (int) column - 65;

        int[][] newMoves = moves;
        newMoves[actualRow][actualColumn] = player.getId();

        this.refresh();
        this.board.writeSchema(newMoves, players);

        turn++;
    }

    public void getMove(Player player) {
        Move move = player.getMove();
        this.saveMove(move.getColumn(), move.getRow(), player);
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

    private boolean checkHorizontal(Player player) {
        int id = player.getId();

        for (int row = 0; row < board.getColumns(); row++) {
            int missingMovesToWin = this.movesToWin;

            for (int column = 0; column < board.getRows(); column++) {
                int fieldValue = moves[row][column];

                if (fieldValue == id) {
                    missingMovesToWin--;
                }
            }

            if (missingMovesToWin == 0) {
                return true;
            }
        }

        return false;
    }

    private boolean checkVertical(Player player) {
        int id = player.getId();

        for (int column = 0; column < board.getColumns(); column++) {
            int missingMovesToWin = this.movesToWin;

            for (int row = 0; row < board.getRows(); row++) {
                int fieldValue = moves[row][column];

                if (fieldValue == id) {
                    missingMovesToWin--;
                }
            }

            if (missingMovesToWin == 0) {
                return true;
            }
        }

        return false;
    }

    private boolean checkDiagonally(Player player) {
        int id = player.getId();
        int column = 0, row = 0;
        int columns = board.getColumns();
        int rows = board.getRows();
        int missingMovesToWin = this.movesToWin;

        // direction: ⬊
        while (column + movesToWin <= columns || row + movesToWin <= rows) {
//            System.out.println("checkDiagonally (to bottom right) - " + column + ":" + row);

            for (int i = row, j = column, move = 1; move <= movesToWin; i++, j++, move++) {
                if (moves[i][j] == id) {
                    missingMovesToWin--;
                }
            }

            column++;
            row++;
        }

        if (missingMovesToWin == 0) {
            return true;
        }

        // direction: ⬋
        missingMovesToWin = this.movesToWin;
        column = columns - 1;
        row = rows - 1;

        while (column - movesToWin >= 0 || row + movesToWin <= rows) {
//            System.out.println("checkDiagonally (to bottom left) - " + column + ":" + row);

            for (int i = row, j = column, move = 1; move <= movesToWin; i++, j--, move++) {
                if (moves[i][j] == id) {
                    missingMovesToWin--;
                }
            }

            column--;
            row++;
        }

        return missingMovesToWin == 0;
    }

    private boolean checkWinner(Player[] players) {
        // limit for two players
        for (int i = 1; i <= 2; i++) {
            Player currentPlayer = players[i];
            boolean isPlayerWon = this.checkHorizontal(currentPlayer) || this.checkVertical(currentPlayer) || this.checkDiagonally(currentPlayer);

            if (isPlayerWon) {
                players[0] = currentPlayer;
                return true;
            }
        }

        return false;
    }

    public void start() {
//        int size = this.readSize();
//        int movesToWin = this.readMovesToWin();
        int size = 3, movesToWinMOCK = 3;

        this.movesToWin = movesToWinMOCK;
        this.board = new Board(size, size, movesToWinMOCK);

        int[][] moves = new int[size][size];
        this.moves = moves;

        this.board.writeSchema(moves, players);

        this.fillFields();

        // player[0] is winner
        while (players[0] == null) {
            for (int i = 1; i <= 2; i++) {
                this.getMove(players[i]);

                if (this.checkWinner(players)) {
                   break;
                }
            }
        }

        this.finish();
    }

    private void finish() {
        System.out.println("The winner is \"" + players[0].getName() + "\" [" + players[0].getSign() + "]");
    }
}
