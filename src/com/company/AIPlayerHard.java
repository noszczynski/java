package com.company;

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

                    model.setMark(row, col, Model.Mark.Player); // "X"
                    int moveValue = minimax(
                        MAX_DEPTH,
                        Integer.MIN_VALUE,
                        Integer.MAX_VALUE,
                        false
                    );
                    model.setMark(row, col, Model.Mark.EMPTY); // " "

                    if (moveValue > bestValue) {
                        bestMove[0] = row;
                        bestMove[1] = col;
                        bestValue = moveValue;
                    }
                }
            }
        }

//        System.out.println("bestMove: " + Arrays.toString(bestMove));
        return bestMove;
    }

    private int minimax(int depth, int alpha, int beta, boolean isMax) {

        System.out.println("\n--- START ---");
        System.out.println("depth: " + depth);

        /* Maximising player, find the maximum attainable value. */
        if (isMax) {
            int highestVal = Integer.MIN_VALUE;

            for (int row = 0; row < model.getWidth(); row++) {
                for (int col = 0; col < model.getWidth(); col++) {

                    if (model.isMarkEmpty(row, col)) {

                        model.setMark(row, col, Model.Mark.Player); // "X"
                        highestVal = Math.max(
                                highestVal,
                                minimax(
                                        depth - 1,
                                        alpha,
                                        beta,
                                        false
                                )
                        );
                        model.setMark(row, col, Model.Mark.EMPTY); // " "
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

                        model.setMark(row, col, Model.Mark.Computer); // "O"
                        lowestVal = Math.min(
                                lowestVal,
                                minimax(
                                        depth - 1,
                                        alpha,
                                        beta,
                                        true
                                )
                        );
                        model.setMark(row, col, Model.Mark.EMPTY); // " "
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
