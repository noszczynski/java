package com.company;

import java.awt.Color;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class View extends JPanel implements ActionListener {

    private final Model model;
    private final JButton[][] squares;
    private final Difficulty difficulty;

    public JLabel resultLabel = new JLabel("Result: ");

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

                /* finish initializing JButton; add to JPanel */
                squaresPanel.add(squares[row][column]);
            }
        }

        resultLabel.setName("resultLabel");
        resultLabel.setFont(new Font("Courier New", Font.PLAIN, 48));
        squaresPanel.add(resultLabel);

        /* add squarePanel to view panel */
        add(squaresPanel);

        if (difficulty == Difficulty.Hard) {
            makeInitialComputerMove();
        }
    }

    public void showResult(String r) {

        /* Display the final winner */
        resultLabel.setText(model.getResult().toString().toUpperCase().trim());
    }

    private void checkResult(int row, int column) {

        model.placeMark(row, column);
        System.out.println(squares[row][column]);
        squares[row][column].setText(model.getMark(row, column).toString());
        System.out.println(squares[row][column]);

        if (model.isMarkWin(model.getMark(row, column))) {
            System.out.println("Wygrał: " + model.getMark(row, column));
            showResult(model.getMark(row, column).toString());
        } else if (model.isTie()) {
            System.out.println("Remis");
            showResult(model.getMark(row, column).toString().toUpperCase().trim());
        } else {
            System.out.println("Gramy dalej");
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

        if (difficulty == Difficulty.Easy) {
            move = AIPlayerEasy.makeMove(model);
        } else {
            move = AIPlayerHard.makeMove(model);
        }

        checkResult(move[0], move[1]);
    }

    private void makeInitialComputerMove() {

        int[] move = AIPlayerEasy.makeMove(model);
        checkResult(move[0], move[1]);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        System.out.println("Log 1");
        System.out.println(model.isPlayerTurn());

        if (model.isPlayerTurn()) {
            makePlayerMove((JButton) e.getSource());
        }
    }
}
