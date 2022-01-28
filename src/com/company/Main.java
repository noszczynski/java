package com.company;

import javax.swing.JFrame;

// TODO działające AI hard
// TODO blokada po wygranej oraz możliwość resetu oraz wprowadzenia wielkości planszy
// TODO lepszy UI
// TODO implementacja algorytmu normal

/**
 * Game created by Adam Noszczyński.
 *
 * This is 2D window classic tic-tac-toe with custom board size <3:10>.
 * In the game, the player will face against AI, which should always at least tie or beat the less experienced player.
 * AI is using the algorithm "Minimax with Alpha-beta Pruning": https://www3.ntu.edu.sg/home/ehchua/programming/java/javagame_tictactoe_ai.html.
 * */

public class Main {
    private static final String GAME_NAME = "Tic Tac Toe";
    private static final String AUTHOR = "Adam Noszczyński";

    public static void main(String[] args) {
        try {
            /* Create game objects */
            View.Difficulty difficulty = View.Difficulty.Hard;
            Model model = new Model(difficulty);
            View view = new View(model, difficulty);


            /* Create the frame */
            JFrame frame = new JFrame(GAME_NAME + " by " + AUTHOR + ". Difficulty " + difficulty);

            /* Set the frame */
            int boardWidth = model.getWidth();
            frame.setSize(boardWidth * 200, boardWidth * 200);

            /* Configure the frame */
            frame.add(view);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
