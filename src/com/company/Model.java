package com.company;

import java.util.Scanner;

public class Model {

    private static final int DEFAULT_WIDTH = 3;

    /* Mark (represents X, O, or an empty square */

    public enum Mark {

        X("X"),
        O("O"),
        EMPTY(" ");

        private final String message;

        private Mark(String msg) {
            message = msg;
        }

        @Override
        public String toString() {
            return message;
        }

    }

    /*
        Result (represents the final state of the game: X wins, O wins, a tie,
        or NONE if the game is not yet over)
   */

    public enum Result {

        X("\nX"),
        O("\nO"),
        TIE("\nTie"),
        NONE("\nnone");

        private final String message;

        private Result(String msg) {
            message = msg.trim();
        }

        @Override
        public String toString() {
            return message;
        }

    }

    private final Mark[][] grid;    /* Game grid */
    private boolean xTurn;          /* True if X is current player */
    private final int width;        /* Size of game grid */

    public Model() {

        /* Initialize width; X goes first */

        int width = getBoardWidth();

        this.width = width;
        xTurn = true;

        /* Create grid (width x width) as a 2D Mark array */
        grid = new Mark[width][width];

        /* Initialize grid by filling every square with empty marks */
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = Mark.EMPTY;
            }
        }

    }

    private int getBoardWidth() {
        Scanner scanner = new Scanner(System.in);
        int width = DEFAULT_WIDTH;

        width = scanner.nextInt();

        while (width < 3 || width > 10) {
            System.out.println("Pole musi być między 3 a 10");
            width = scanner.nextInt();
        }

        return width;
    }

    public void makeMark(int row, int col) {

         /*
            Place the current player's mark in the square at the specified
            location, but only if the location is valid and if the square is
            empty!
         */

        if (isValidSquare(row, col) && (!isSquareMarked(row, col))) {
            if (xTurn) {
                grid[row][col] = Mark.X;
                xTurn = false;
            } else {
                grid[row][col] = Mark.O;
                xTurn = true;
            }
        }
    }

    public boolean isValidSquare(int row, int col) {

        /* Return true if specified location is within grid bounds */
        boolean ifRow = row >= 0 && row <= width - 1;
        boolean ifCol = col >= 0 && col <= width - 1;

        return ifRow && ifCol && !isSquareMarked(row, col);
    }

    public boolean isSquareMarked(int row, int col) {

        /* Return true if square at specified location is marked */
        return getMark(row, col) != Mark.EMPTY;
    }

    public Mark getMark(int row, int col) {

        System.out.println(row + ":" + col);
        /* Return mark from the square at the specified location */
        return grid[row][col];
    }

    public Result getResult() {
        
        /*
            Use isMarkWin() to see if X or O is the winner, if the game is a
            tie, or if the game is not over, and return the corresponding Result
            value
        */

        if (isMarkWin(Mark.X)) {
            return Result.X;
        } else if (isMarkWin(Mark.O)) {
            return Result.O;
        } else if (isTie()) {
            return Result.TIE;
        } else {
            return Result.NONE;
        }
    }

    private boolean checkWinHorizontal(Mark mark) {
        int marksPerRow = 0;

        for (int row = 0; row < width; row++) {
            for (int col = 0; col < width; col++) {
                if (getMark(row, col) == mark) {
                    marksPerRow++;
                } else {
                    marksPerRow = 0;
                }
                if (marksPerRow == (width)) {
                    return true;
                }
            }
            marksPerRow = 0;
        }

        return false;
    }

    private boolean checkWinVertical(Mark mark) {
        int marksPerRow = 0;

        for (int col = 0; col < width; col++) {
            for (int row = 0; row < width; row++) {
                if (getMark(row, col) == mark) {
                    marksPerRow++;
                } else {
                    marksPerRow = 0;
                }
                if (marksPerRow == (width)) {
                    return true;
                }
            }
            marksPerRow = 0;
        }

        return false;
    }

    private boolean checkWinDiagonalFromTopLeftToBottomRight(Mark mark) {
        int marksPerRow = 0;

        for (int row = 0; row < width; row++) {
            if (getMark(row, row) == mark) {
                marksPerRow++;
            } else {
                marksPerRow = 0;
            }
            if (marksPerRow == (width)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkWinDiagonalFromBottomLeftToTopRight(Mark mark) {
        int marksPerRow = 0;

        for (int i = 0; i < width; i++) {
            if (getMark(i, width - 1 - i) == mark) {
                marksPerRow++;
            } else {
                marksPerRow = 0;
            }
            if (marksPerRow == (width)) {
                return true;
            }
        }

        return false;
    }

    protected boolean isMarkWin(Mark mark) {
        
        /*
            Check the squares of the board to see if the specified mark is the
            winner
        */
        boolean isWinHorizontal = checkWinHorizontal(mark);
        boolean isWinVertical = checkWinVertical(mark);
        boolean isWinDiagonalFromTopLeftToBottomRight = checkWinDiagonalFromTopLeftToBottomRight(mark);
        boolean isWinDiagonalFromBottomLeftToTopRight = checkWinDiagonalFromBottomLeftToTopRight(mark);

        return isWinHorizontal || isWinVertical || isWinDiagonalFromTopLeftToBottomRight || isWinDiagonalFromBottomLeftToTopRight;
    }

    protected boolean isTie() {

        /* Check the squares of the board to see if the game is a tie */
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                if (getMark(i, j) == Mark.EMPTY) {
                    return false;
                }
            }
        }

        return !isMarkWin(Mark.X) && !isMarkWin(Mark.O);
    }

    public boolean isGameOver() {
        return Result.NONE != getResult();
    }

    public boolean isXTurn() {
        return xTurn;
    }

    public int getWidth() {
        return width;
    }

}
