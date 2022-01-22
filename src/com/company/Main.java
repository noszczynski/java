package com.company;

import javax.swing.JFrame;

// TODO przetestować większe pola gry oraz 4 do końca
// TODO dodać algorytm ruchów komputera

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
            Model model = new Model();
            View view = new View(model, View.Difficulty.Hard);


            /* Create the frame */
            JFrame frame = new JFrame(GAME_NAME + " by " + AUTHOR);

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
