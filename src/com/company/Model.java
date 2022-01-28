package com.company;

import java.util.Scanner;

import static com.company.Mark.*;

public class Model {

    private static final int DEFAULT_MIN_WIDTH = 3;
    private static final int DEFAULT_MAX_WIDTH = 10;
    private static final int DEFAULT_WIDTH = 3;

    /*
        Result (represents the final state of the game: X wins, O wins, a draw,
        or NONE if the game is not yet over)
   */

    private final Mark[][] grid;    /* Game grid */
    private boolean isPlayerTurn;   /* True if Player is current player */
    private final int width;        /* Size of game grid */
    private int availableMoves;     /* Moves to end game */

    public Model(Difficulty difficulty) {

        /* Initialize width */
        int width = requestBoardWidth();
        this.width = width;

        availableMoves = width * width;

        /* Computer starts only on hard difficulty */
        isPlayerTurn = difficulty != Difficulty.Hard;

        /* Create grid (width x width) as a 2D Mark array */
        grid = new Mark[width][width];

        /* Initialize grid by filling every square with empty marks */
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = EMPTY;
            }
        }
    }

    private int requestBoardWidth() {
        Scanner scanner = new Scanner(System.in);
        int width = 0;
        int outrage = 3;

        System.out.println();
        System.out.println("Choose Board size: " + DEFAULT_MIN_WIDTH + "-" + DEFAULT_MAX_WIDTH);
        System.out.print("Your choice: ");

        try {

            width = scanner.nextInt();

            while ((width < DEFAULT_MIN_WIDTH || width > DEFAULT_MAX_WIDTH) && outrage > 0) {

                if (--outrage == 0) {

                    System.out.println("If you are unable to choose by yourself, the system has selected a default value for you: " + DEFAULT_WIDTH);
                    width = DEFAULT_WIDTH;
                } else {

                    System.out.println("Error! Use integer values between " + DEFAULT_MIN_WIDTH + " and " + DEFAULT_MAX_WIDTH + ". " + outrage + " tries left.");
                    System.out.print("Your choice: ");

                    width = scanner.nextInt();
                }
            }

        } catch (Exception e) {
            System.out.println("Fatal Error! Use integer values between " + DEFAULT_MIN_WIDTH + " and " + DEFAULT_MAX_WIDTH);
        }

        return width;
    }

    public void setMark(int row, int column, Mark mark) {

        grid[row][column] = mark;
    }

    public void placeMark(int row, int column) {

         /*
            Place the current player's mark in the square at the specified
            location, but only if the location is valid and if the square is
            empty!
         */

        if (isValidSquare(row, column)) {
            if (isPlayerTurn) {
                grid[row][column] = Player;
                isPlayerTurn = false;
            } else {
                grid[row][column] = Computer;
                isPlayerTurn = true;
            }

            availableMoves--;
        }
    }

    public boolean isValidSquare(int row, int column) {

        /* Return true if specified location is within grid bounds */
        boolean ifRow = row >= 0 && row <= width - 1;
        boolean ifCol = column >= 0 && column <= width - 1;

        return ifRow && ifCol && isMarkEmpty(row, column);
    }

    public Mark getMark(int row, int column) {

        /* Return mark from the square at the specified location */
        return grid[row][column];
    }

    public Result getResult() {
        
        /*
            Use isMarkWin() to see if X or O is the winner, if the game is a
            draw, or if the game is not over, and return the corresponding Result
            value
        */

        if (isMarkWin(Player)) {
            availableMoves = 0;
            return Result.Player;
        } else if (isMarkWin(Computer)) {
            availableMoves = 0;
            return Result.Computer;
        } else if (isDraw()) {
            availableMoves = 0;
            return Result.DRAW;
        } else {
            return Result.NONE;
        }
    }

    private boolean checkWinHorizontal(Mark mark) {
        int marksPerRow = 0;

        for (int row = 0; row < width; row++) {
            for (int column = 0; column < width; column++) {
                if (getMark(row, column) == mark) {
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

        for (int column = 0; column < width; column++) {
            for (int row = 0; row < width; row++) {
                if (getMark(row, column) == mark) {
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

        /* Check the squares of the board to see if the specified mark is the winner */
        boolean isWinHorizontal = checkWinHorizontal(mark);
        boolean isWinVertical = checkWinVertical(mark);
        boolean isWinDiagonalFromTopLeftToBottomRight = checkWinDiagonalFromTopLeftToBottomRight(mark);
        boolean isWinDiagonalFromBottomLeftToTopRight = checkWinDiagonalFromBottomLeftToTopRight(mark);

        return isWinHorizontal || isWinVertical || isWinDiagonalFromTopLeftToBottomRight || isWinDiagonalFromBottomLeftToTopRight;
    }

    protected boolean isDraw() {

        /* Check the squares of the board to see if the game is a draw */
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                if (getMark(i, j) == EMPTY) {
                    return false;
                }
            }
        }

        return !isMarkWin(Player) && !isMarkWin(Computer);
    }

    public boolean isPlayerTurn() {

        return isPlayerTurn;
    }

    public boolean anyMovesAvailable() {

        return availableMoves > 0;
    }

    public boolean isMarkEmpty(int row, int column) {

        return getMark(row, column) == EMPTY;
    }

    public int getWidth() {

        return width;
    }
}
