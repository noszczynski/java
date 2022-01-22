package com.company;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AIPlayerHard implements AIPlayer {
    private final Model model;
    private final Model.Mark[][] moves;

    public AIPlayerHard(Model model) {
        this.model = model;
        this.moves = model.getGrid();
    }

    private List<int[]> generateMoves() {
        List<int[]> list = Collections.emptyList();

        for (int row = 0; row < model.getWidth(); row++) {
            for (int column = 0; column < model.getWidth(); column++) {
                if (model.isMarkEmpty(row, column)) {
                    int[] element = {row, column};
                    list.add(element);
                }
            }
        }

        System.out.println(list);

        return list;
    }

    /* Get next best move for computer. Return int[2] of {row, col} */
    public int[] makeMove() {
        /* depth, max-turn, alpha, beta */
        int[] result = minimax(2, Model.Mark.Player, Integer.MIN_VALUE, Integer.MAX_VALUE);

        /* row, column */
        return new int[]{
                result[1],
                result[2]
        };
    }

    private int evaluate() {
        return 0;
    }

    /**
     * Minimax (recursive) at level of depth for maximizing or minimizing player
     * with alpha-beta cut-off. Return int[3] of {score, row, col}
     */
    private int[] minimax(int depth, Model.Mark mark, int alpha, int beta) {
        // Generate possible next moves in a list of int[2] of {row, col}.
        List<int[]> nextMoves = generateMoves();

        // mySeed is maximizing; while oppSeed is minimizing
        int score;
        int bestRow = -1;
        int bestCol = -1;

        if (nextMoves.isEmpty() || depth == 0) {
            // Game over or depth reached, evaluate score
            score = evaluate();
            return new int[]{
                    score,
                    bestRow,
                    bestCol
            };
        } else {
            for (int[] move : nextMoves) {
                // try this move for the current "player"
                moves[move[0]][move[1]] = mark;
                if (mark == Model.Mark.Player) {  // mySeed (computer) is maximizing player
                    score = minimax(depth - 1, Model.Mark.Computer, alpha, beta)[0];
                    if (score > alpha) {
                        alpha = score;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                } else {  // oppSeed is minimizing player
                    score = minimax(depth - 1, Model.Mark.Player, alpha, beta)[0];
                    if (score < beta) {
                        beta = score;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                }
                // undo move
                moves[move[0]][move[1]] = Model.Mark.EMPTY;
                // cut-off
                if (alpha >= beta) break;
            }
            return new int[]{(mark == Model.Mark.Player) ? alpha : beta, bestRow, bestCol};
        }
    }
}
