package com.company;

import java.lang.*;

import static com.company.Mark.*;

public class AIPlayerHard implements AIPlayer {
    private static final int MAX_DEPTH = 12;

    public AIPlayerHard() {
    }

    /* Get best move for computer and return int[2] of {row, col} */
    public static int[] makeMove(Model model) {

        int[] bestMove = new int[]{-1, -1};
        int bestValue = Integer.MIN_VALUE;

        for (int row = 0; row < model.getWidth(); row++) {
            for (int col = 0; col < model.getWidth(); col++) {
                if (model.isMarkEmpty(row, col)) {

                    model.setMark(row, col, Computer);
                    int moveValue = minimax(
                            model,
                            MAX_DEPTH,
                            Integer.MIN_VALUE,
                            Integer.MAX_VALUE,
                            false
                    );
                    model.setMark(row, col, EMPTY);
                    if (moveValue > bestValue) {
                        bestMove[0] = row;
                        bestMove[1] = col;
                        bestValue = moveValue;
                    }
                }
            }
        }

        return bestMove;
    }

    /**
     * Evaluate the given board from the perspective of the X player, return
     * 10 if a winning board configuration is found, -10 for a losing one and 0
     * for a draw.
     *
     * @return value of the board
     */
    private static int evaluateModel(Model model, int depth) {
        int rowSum = 0;
        int bWidth = model.getWidth();

        int playerWin = Player.getMark() * bWidth;
        int aiWin = Computer.getMark() * bWidth;

        // Check rows for winner.
        for (int row = 0; row < bWidth; row++) {
            for (int col = 0; col < bWidth; col++) {
                rowSum += model.getMark(row, col).getMark();
            }


            if (rowSum == aiWin) {
                return 10 + depth;
            }

            if (rowSum == playerWin) {
                return -10 - depth;
            }

            rowSum = 0;
        }

        // Check columns for winner.
        rowSum = 0;
        for (int col = 0; col < bWidth; col++) {
            for (int row = 0; row < bWidth; row++) {
                rowSum += model.getMark(row, col).getMark();
            }


            if (rowSum == aiWin) {
                return 10 + depth;
            }

            if (rowSum == playerWin) {
                return -10 - depth;
            }

            rowSum = 0;
        }

        // Check diagonals for winner.
        // Top-left to bottom-right diagonal.
        rowSum = 0;
        for (int i = 0; i < bWidth; i++) {
            rowSum += model.getMark(i, i).getMark();
        }


        if (rowSum == aiWin) {
            return 10 + depth;
        }

        if (rowSum == playerWin) {
            return -10 - depth;
        }

        // Top-right to bottom-left diagonal.
        rowSum = 0;
        int indexMax = bWidth - 1;

        for (int i = 0; i <= indexMax; i++) {
            rowSum += model.getMark(i, indexMax - i).getMark();
        }


        if (rowSum == aiWin) {
            return 10 + depth;
        }

        if (rowSum == playerWin) {
            return -10 - depth;
        }

        return 0;
    }

    private static int minimax(Model model, int depth, int alpha, int beta, boolean isMax) {

        int boardVal = evaluateModel(model, depth);


        if (Math.abs(boardVal) > 0 || depth == 0 || !model.anyMovesAvailable()) {
            return boardVal;
        }

        /* Maximising player, find the maximum attainable value. */
        if (isMax) {
            int highestVal = Integer.MIN_VALUE;

            for (int row = 0; row < model.getWidth(); row++) {
                for (int col = 0; col < model.getWidth(); col++) {

                    if (model.isMarkEmpty(row, col)) {

                        model.setMark(row, col, Computer);
                        highestVal = Math.max(
                                highestVal,
                                minimax(
                                        model,
                                        depth - 1,
                                        alpha,
                                        beta,
                                        false
                                )
                        );
                        model.setMark(row, col, EMPTY);
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

                        model.setMark(row, col, Player);
                        lowestVal = Math.min(
                                lowestVal,
                                minimax(
                                        model,
                                        depth - 1,
                                        alpha,
                                        beta,
                                        true
                                )
                        );
                        model.setMark(row, col, EMPTY);
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
