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

    private void refresh() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void saveMove(int column, int row, Player player) {
        int[][] newMoves = moves;
        newMoves[row][column] = player.getId();

        this.refresh();
        this.board.writeSchema(newMoves, players);

        turn++;
    }

    public void makeMove(Player player) {
        Move move = null;
        boolean correct = false;

        System.out.println("Ruch gracza " + player.getName() + ": ");

        while (!correct) {
            move = player.getMove(moves, board.getColumns(), board.getRows());

            if (!move.isOk()) {
                if (!(player instanceof ComputerPlayer)) {
                    System.out.println("Nieprawidłowy ruch!");
                }
            } else {
                correct = true;
            }
        }

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

    // TODO player.id?
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
                System.out.println("Won by checkHorizontal in row: " + row);
                return true;
            }
        }

        return false;
    }

    // TODO player.id?
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
                System.out.println("Won by checkVertical in column: " + column);
                return true;
            }
        }

        return false;
    }

    // TODO player.id?
    private boolean checkDiagonally(Player player) {
        int id = player.getId();
        int column = 0, row = 0;
        int columns = board.getColumns();
        int rows = board.getRows();
        int missingMovesToWin = this.movesToWin;

        // direction: ⬊
        while (column + movesToWin <= columns || row + movesToWin <= rows) {
            missingMovesToWin = this.movesToWin;

            for (
                    int i = 0, j = column, move = 1;
                    move <= movesToWin && column + movesToWin <= columns && row + movesToWin <= rows;
                    i++, j++, move++
            ) {
                System.out.println(i + ":" + j);
                if (moves[i][j] == id) {
                    missingMovesToWin--;
                }
            }

            column++;

            if (column == columns) {
                column = 0;
                row++;
            }
        }

        if (missingMovesToWin == 0) {
            System.out.println("Won by checkDiagonally (⬊) \nin column: " + column + " and row: " + row);
            return true;
        }

        // direction: ⬋
        column = columns - 1;
        row = rows - 1;

        while (column - movesToWin >= 0 || row + movesToWin <= rows) {
            missingMovesToWin = this.movesToWin;

            for (
                    int i = 0, j = column, move = 1;
                    move <= movesToWin && column - movesToWin >= 0 && row + movesToWin <= rows;
                    i++, j--, move++
            ) {
                if (moves[i][j] == id) {
                    System.out.println("Won by checkDiagonally (⬋) \nin column: " + column + " and row: " + row);
                    missingMovesToWin--;
                }
            }

            column--;

            if (column == -1) {
                column = columns - 1;
                row++;
            }
        }

        return missingMovesToWin == 0;
    }

    private boolean checkWinner(Player[] players) {
        // limit for two players
        for (int i = 1; i <= 2; i++) {
            Player currentPlayer = players[i];
            boolean isWonHorizontal = this.checkHorizontal(currentPlayer);
            boolean isWonVertical = this.checkVertical(currentPlayer);
            boolean isWonDiagonally = this.checkDiagonally(currentPlayer);
            boolean isPlayerWon = isWonHorizontal || isWonVertical || isWonDiagonally;

            if (isPlayerWon) {
                if (isWonHorizontal) System.out.println("Won by horizontal match!");
                if (isWonVertical) System.out.println("Won by vertical match!");
                if (isWonDiagonally) System.out.println("Won by diagonally match!");

                players[0] = currentPlayer;
                return true;
            }
        }

        return false;
    }

    public void start() {
//        int size = this.readSize();
//        int movesToWin = this.readMovesToWin();
        int size = 4, movesToWinMOCK = 3;

        this.movesToWin = movesToWinMOCK;
        this.board = new Board(size, size, movesToWinMOCK);

        int[][] moves = new int[size][size];
        this.moves = moves;

        this.board.writeSchema(moves, players);

        this.fillFields();

        // player[0] is winner
        while (players[0] == null) {
            for (int i = 1; i <= 2; i++) {
                this.makeMove(players[i]);

                if (turn >= movesToWin * 2 - 1 && this.checkWinner(players)) {
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
