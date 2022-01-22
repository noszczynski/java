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

    /* Result (represents the final state of the game: X wins, O wins, a tie,
       or NONE if the game is not yet over) */

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

    private final Mark[][] grid; /* Game grid */
    private boolean xTurn; /* True if X is current player */
    private final int width;     /* Size of game grid */

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
        
        /* Place the current player's mark in the square at the specified
           location, but only if the location is valid and if the square is
           empty! */

        if (isValidSquare(row, col) && (!isSquareMarked(row, col))) {
            if (xTurn) {
                grid[row][col] = Mark.X;
                xTurn = false;
//					return true;
            } else {
                grid[row][col] = Mark.O;
                xTurn = true;
//					return true;
            }
        }
    }

    public boolean isValidSquare(int row, int col) {

        /* Return true if specified location is within grid bounds */
        if (row >= 0 && row <= width - 1 && col >= 0 && col <= width - 1 && !isSquareMarked(row, col)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isSquareMarked(int row, int col) {

        /* Return true if square at specified location is marked */
        return getMark(row, col) != Mark.EMPTY;
    }

    public Mark getMark(int row, int col) {

        System.out.println(row +":"+ col);
        /* Return mark from the square at the specified location */
        return grid[row][col];

    }

    public Result getResult() {
        
        /* Use isMarkWin() to see if X or O is the winner, if the game is a
           tie, or if the game is not over, and return the corresponding Result
           value */

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

    protected boolean isMarkWin(Mark mark) {
        
        /* Check the squares of the board to see if the specified mark is the
           winner */

        int countMarks = 0;
        //check win horizontal
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < width; col++) {
                if (getMark(row, col) == mark) {
                    countMarks++;
                } else {
                    countMarks = 0;
                }
                if (countMarks == (width)) {
                    return true;
                }
            }
            countMarks = 0;
        }
        countMarks = 0;
        //check win vertical
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < width; row++) {
                if (getMark(row, col) == mark) {
                    countMarks++;
                } else {
                    countMarks = 0;
                }
                if (countMarks == (width)) {
                    return true;
                }
            }
            countMarks = 0;
        }
        countMarks = 0;
        //check diagonal from top left to bottom right
        for (int row = 0; row < width; row++) {
            if (getMark(row, row) == mark) {
                countMarks++;
            } else {
                countMarks = 0;
            }
            if (countMarks == (width)) {
                return true;
            }
        }
        countMarks = 0;

        //check diagonal from bottom left to top right
        for (int i = 0; i < width; i++) {
            if (getMark(i, width - 1 - i) == mark) {
                countMarks++;
            } else {
                countMarks = 0;
            }
            if (countMarks == (width)) {
                return true;
            }
        }
		/*
        for(int col = (width-1); col >=0; col--){
            int row = 0;
			if(getMark(row, col) == mark){
				countMarks++;
			}
			if(countMarks == width){
				return true;
			}
			row++;
			
        }
		*/
        countMarks = 0;
        return false;
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
        if (isMarkWin(Mark.X) || isMarkWin(Mark.O)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isGameover() {
        return Result.NONE != getResult();
    }

    public boolean isXTurn() {

        /* Getter for xTurn */

        return xTurn;

    }

    public int getWidth() {

        /* Getter for width */

        return width;

    }

}
