package com.company;

import java.util.Arrays;

public class AIPlayerHard implements AIPlayer {
    private static final int MAX_DEPTH = 12;

    private final Model model;

    public AIPlayerHard(Model model) {
        this.model = model;
    }

    /* Get best move for computer and return int[2] of {row, col} */
    public int[] makeMove() {

        int[] bestMove = new int[]{-1, -1};
        int bestValue = Integer.MIN_VALUE;

        for (int row = 0; row < model.getWidth(); row++) {
            for (int col = 0; col < model.getWidth(); col++) {

                if (model.isMarkEmpty(row, col)) {

                    model.setMark(row, col, Mark.Player); // "X"
                    int moveValue = minimax(
                            MAX_DEPTH,
                            Integer.MIN_VALUE,
                            Integer.MAX_VALUE,
                            false
                    );
                    model.setMark(row, col, Mark.EMPTY); // " "

                    if (moveValue > bestValue) {
                        bestMove[0] = row;
                        bestMove[1] = col;
                        bestValue = moveValue;
                    }
                }
            }
        }

        System.out.println("bestMove: " + Arrays.toString(bestMove));
        return bestMove;
    }

    /**
     * Evaluate the given board from the perspective of the X player, return
     * 10 if a winning board configuration is found, -10 for a losing one and 0
     * for a draw.
     *
     * @return value of the board
     */
    private int evaluateBoard() {
        int rowSum = 0;
        int bWidth = model.getWidth();

        int playerNumber = 1, aiNumber = -1;
        int playerWin = playerNumber * bWidth;
        int aiWin = aiNumber * bWidth;

        // Check rows for winner.
        for (int row = 0; row < bWidth; row++) {
            for (int col = 0; col < bWidth; col++) {
                Mark mark = model.getMark(row, col);
                if (mark == Mark.Player) rowSum += playerNumber;
                else if (mark == Mark.Computer) rowSum += aiNumber;
            }

            if (rowSum == playerWin) {
                return 10;
            }

            if (rowSum == aiWin) {
                return -10;
            }

            rowSum = 0;
        }

        // Check columns for winner.
        rowSum = 0;
        for (int col = 0; col < bWidth; col++) {
            for (int row = 0; row < bWidth; row++) {
                Mark mark = model.getMark(row, col);
                if (mark == Mark.Player) rowSum += playerNumber;
                else if (mark == Mark.Computer) rowSum += aiNumber;
            }

            if (rowSum == playerWin) {
                return 10;
            }

            if (rowSum == aiWin) {
                return -10;
            }

            rowSum = 0;
        }

        // Check diagonals for winner.
        // Top-left to bottom-right diagonal.
        rowSum = 0;
        for (int i = 0; i < bWidth; i++) {
            Mark mark = model.getMark(i, i);
            if (mark == Mark.Player) rowSum += playerNumber;
            else if (mark == Mark.Computer) rowSum += aiNumber;
        }

        if (rowSum == playerWin) {
            return 10;
        }

        if (rowSum == aiWin) {
            return -10;
        }

        // Top-right to bottom-left diagonal.
        rowSum = 0;
        int indexMax = bWidth - 1;

        for (int i = 0; i <= indexMax; i++) {
            Mark mark = model.getMark(i, indexMax - i);
            if (mark == Mark.Player) rowSum += playerNumber;
            else if (mark == Mark.Computer) rowSum += aiNumber;
        }

        if (rowSum == playerWin) {
            return 10;
        }

        if (rowSum == aiWin) {
            return -10;
        }

        return 0;
    }

    private int minimax(int depth, int alpha, int beta, boolean isMax) {

        int boardVal = evaluateBoard();

        if (Math.abs(boardVal) == 10 || depth == 0
//                || !board.anyMovesAvailable()
        ) {
            return boardVal;
        }

        /* Maximising player, find the maximum attainable value. */
        if (isMax) {
            int highestVal = Integer.MIN_VALUE;

            for (int row = 0; row < model.getWidth(); row++) {
                for (int col = 0; col < model.getWidth(); col++) {

                    if (model.isMarkEmpty(row, col)) {

                        model.setMark(row, col, Mark.Player); // "X"
                        highestVal = Math.max(
                                highestVal,
                                minimax(
                                        depth - 1,
                                        alpha,
                                        beta,
                                        false
                                )
                        );
                        model.setMark(row, col, Mark.EMPTY); // " "
                        alpha = Math.max(alpha, highestVal);

                        if (alpha >= beta) {
                            return highestVal;
                        }
                    }

                }
            }

            return highestVal;

            /* Minimising player, find the minimum attainable value; */
        } else {
            int lowestVal = Integer.MAX_VALUE;

            for (int row = 0; row < model.getWidth(); row++) {
                for (int col = 0; col < model.getWidth(); col++) {

                    if (model.isMarkEmpty(row, col)) {

                        model.setMark(row, col, Mark.Computer); // "O"
                        lowestVal = Math.min(
                                lowestVal,
                                minimax(
                                        depth - 1,
                                        alpha,
                                        beta,
                                        true
                                )
                        );
                        model.setMark(row, col, Mark.EMPTY); // " "
                        beta = Math.min(beta, lowestVal);

                        if (beta <= alpha) {
                            return lowestVal;
                        }
                    }
                }
            }

            return lowestVal;
        }
    }
}
