package com.company;

import java.awt.Color;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class View extends JPanel implements ActionListener {
    public enum Difficulty {

        Easy("Easy"),
        Normal("Normal"),
        Hard("Hard");

        private final String message;

        private Difficulty(String msg) {
            message = msg;
        }

        @Override
        public String toString() {
            return message;
        }
    }

    private final Model model;
    private final JButton[][] squares;
    private final Difficulty difficulty;

    public JLabel resultLabel = new JLabel("Result");

    public View(Model model, Difficulty difficulty) {

        this.model = model;
        this.difficulty = difficulty;
        int modelWidth = this.model.getWidth();

        JPanel squaresPanel = new JPanel(new GridLayout(modelWidth + 1, modelWidth + 1));

        squares = new JButton[modelWidth][modelWidth];

        /* loop through every row and column */
        for (int row = 0; row < modelWidth; row++) {
            for (int column = 0; column < modelWidth; column++) {

                JButton button = new JButton();
                button.setBorder(new LineBorder(Color.BLACK));
                button.setPreferredSize(new Dimension(124, 124));
                button.addActionListener(this);
                button.setName("Square[" + row + "][" + column + "]");

                squares[row][column] = button;

                /* testing the creation of buttons */
//                System.out.println("Created Square [" + row + ":" + column + "]");

                /* finish initializing JButton; add to JPanel */
                squaresPanel.add(squares[row][column]);
//                System.out.println("Square [" + row + ":" + column + "] has been added to the panel.");
            }
        }

        resultLabel.setName("resultLabel");
        resultLabel.setFont(new Font("Courier New", Font.PLAIN, 48));
        squaresPanel.add(resultLabel);

        /* add squarePanel to view panel */
        add(squaresPanel);
    }

//    public void showNextMovePrompt() {
//
//        /* Display a prompt for the player's next move (see examples) */
//        if (model.isXTurn()) {
//            System.out.print("\nPlayer 1 (X) move: \nEnter the row and column numbers, separated by a space: ");
//        } else {
//            System.out.print("\nPlayer 2 (O) move: \nEnter the row and column numbers, separated by a space: ");
//        }
//    }

    public void showResult(String r) {

        /* Display the final winner */
        resultLabel.setText(model.getResult().toString().toUpperCase().trim());
    }

    private void checkResult(int row, int column) {

        model.placeMark(row, column);
        System.out.println(squares[row][column]);
        squares[row][column].setText(model.getMark(row, column).toString());

        if (model.isMarkWin(model.getMark(row, column))) {
            showResult(model.getMark(row, column).toString());
        } else if (model.isTie()) {
            showResult(model.getMark(row, column).toString().toUpperCase().trim());
        }
    }

    private void makePlayerMove(JButton clickedBtn) {

        int row = (int) (clickedBtn.getName().charAt(7)) - 48;
        int column = (int) (clickedBtn.getName().charAt(10)) - 48;

        checkResult(row, column);
        makeComputerMove();
    }

    private void makeComputerMove() {

        int[] move;

        if (difficulty == Difficulty.Hard) {
            move = AIPlayerHard.makeMove(model);
        } else if (difficulty == Difficulty.Normal) {
            move = AIPlayerNormal.makeMove(model);
        } else {
            move = AIPlayerEasy.makeMove(model);
        }

        checkResult(move[0], move[1]);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (model.isPlayerTurn()) {
            makePlayerMove((JButton) e.getSource());
        }
    }
}
