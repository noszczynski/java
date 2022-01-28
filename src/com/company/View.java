package com.company;

import java.awt.Color;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class View extends JPanel implements ActionListener {
    private final Model model;
    private final JButton[][] buttons;
    private final Difficulty difficulty;

    public View() {

        Difficulty difficulty = requestDifficulty();

        this.model = new Model(difficulty);
        this.difficulty = difficulty;

        int modelWidth = model.getWidth();

        /* head panel */
        JPanel squaresPanel = new JPanel(new GridLayout(modelWidth + 1, modelWidth + 1));

        /* difficulty panel */
//        JPanel difficultyPanel = new JPanel(new GridLayout(1, 1));
//        ButtonGroup difficultyGroup = new ButtonGroup();

//        for (int i = 0; i < 2; i++) {
//            JRadioButton input;
//
//            input = new JRadioButton("Difficulty " + i);
//            input.setPreferredSize(new Dimension(124, 64));
//
//            difficultyGroup.add(input);
//            difficultyPanel.add(input);
//        }

        /* accept panel */
//        JButton acceptButton = new JButton();
//        acceptButton.setPreferredSize(new Dimension(124, 64));
//        acceptButton.setBorder(new LineBorder(Color.BLACK));
//        acceptButton.addActionListener(this);
//        acceptButton.setText("Start");
//        difficultyPanel.add(acceptButton);

        /* buttons panel */
        buttons = new JButton[modelWidth][modelWidth];

        /* loop through every row and column */
        for (int row = 0; row < modelWidth; row++) {
            for (int column = 0; column < modelWidth; column++) {

                JButton button = new JButton();
                button.setBorder(new LineBorder(Color.BLACK));
                button.setPreferredSize(new Dimension(124, 124));
                button.addActionListener(this);
                button.setName("Square[" + row + "][" + column + "]");

                buttons[row][column] = button;

                /* finish initializing JButton; add to JPanel */
                squaresPanel.add(buttons[row][column]);
            }
        }

        /* add difficultyPanel to view panel */
//        add(difficultyPanel);

        System.out.println("Starting game...");

        /* add squarePanel to view panel */
        add(squaresPanel);

        if (difficulty == Difficulty.Hard) {
            makeInitialComputerMove();
        }
    }

    private Difficulty requestDifficulty() {
        Scanner scanner = new Scanner(System.in);
        Difficulty[] options = {Difficulty.Easy, Difficulty.Normal, Difficulty.Hard};

        int userOption = 0;
        int outrage = 3;

        System.out.println("Choose Difficulty level:");
        System.out.println("1. Easy");
        System.out.println("2. Normal");
        System.out.println("3. Hard");
        System.out.print("Your choice: ");

        try {

            userOption = scanner.nextInt();

            while (userOption < 1 || userOption > options.length) {
                if (--outrage == 0) {

                    System.out.println("If you are unable to choose by yourself, the system has selected a default value for you: " + Difficulty.Easy);
                    userOption = 1;
                } else {

                    System.out.println("Error! Use integer values between " + 1 + " and " + 3 + ". " + outrage + " tries left.");
                    System.out.print("Your choice: ");
                    userOption = scanner.nextInt();
                }
            }

            System.out.println("You choose: " + options[userOption - 1]);
        } catch (Exception e) {
            System.out.println("Fatal Error! Use integer values between " + 1 + " and " + 3);
        }

        return options[userOption - 1];
    }

    public Model getModel() {

        return model;
    }

    public void showResult() {

        /* Display the final winner */
        JFrame frame = new JFrame();
        JDialog dialog = new JDialog(frame, "Winner");

        dialog.setSize(new Dimension(256, 128));
        dialog.setVisible(true);
        dialog.add(new JLabel("Winner is " + model.getResult().toString().trim()));

        add(dialog);
    }

    private void checkResult(int row, int column) {

        model.placeMark(row, column);
        buttons[row][column].setText(model.getMark(row, column).toString());

        if (model.isMarkWin(model.getMark(row, column)) || model.isTie()) {
            showResult();
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

        if (model.isPlayerTurn()) {
            makePlayerMove((JButton) e.getSource());
        }
    }
}
