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

    private int maxAIDepth = 6;

    public View() {

        Difficulty difficulty = requestDifficulty();
        this.difficulty = difficulty;

        this.model = new Model(difficulty);
        int modelWidth = model.getWidth();

        if (difficulty != Difficulty.Easy) {
            this.maxAIDepth = requestMaxAIDepth();
        }

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

    private int requestMaxAIDepth() {
        Scanner scanner = new Scanner(System.in);

        int
                userOption = 0,
                outrage = 3,
                modelWidth = model.getWidth(),
                max = modelWidth * modelWidth,
                min = difficulty == Difficulty.Hard ? (int) Math.floor(max / 2) : 1;

        System.out.println();
        System.out.println(
                "Determine how many moves AI should predict forward." +
                        "\nThe Hard difficulty level does not allow for less than the board size minus two." +
                        "\nYour minimum is: " + min +
                        "\nYour maximum is: " + max +
                        "\nRemember - Value higher than 12 may slow game down seriously."
        );
        System.out.print("Your choice: ");

        try {

            userOption = scanner.nextInt();

            while ((userOption < min || userOption > max) && outrage > 0) {

                if (--outrage == 0) {
                    int defaultDepth = Math.min(max, 8);
                    System.out.println("If you are unable to choose by yourself, the system has selected a default value for you: " + defaultDepth);
                    userOption = defaultDepth;
                } else {

                    System.out.println("Error! Use integer values between " + min + " and " + max + ". " + outrage + " tries left.");
                    System.out.print("Your choice: ");

                    userOption = scanner.nextInt();
                }
            }

        } catch (Exception e) {
            System.out.println("Fatal Error! Use integer values between " + min + " and " + max + ". " + outrage + " tries left.");
        }

        return userOption;
    }

    private Difficulty requestDifficulty() {
        Scanner scanner = new Scanner(System.in);
        Difficulty[] options = {Difficulty.Easy, Difficulty.Normal, Difficulty.Hard};

        int userOption = 0;
        int outrage = 3;

        System.out.println();
        System.out.println("Choose Difficulty level:");
        System.out.println("1. Easy");
        System.out.println("2. Normal");
        System.out.println("3. Hard");
        System.out.print("Your choice: ");

        try {

            userOption = scanner.nextInt();

            while ((userOption < 1 || userOption > options.length) && outrage > 0) {
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

        System.out.println(model.isDraw() + ":" + model.getResult().toString().trim());
        dialog.setVisible(true);

        if (model.isDraw()) {
            dialog.add(new JLabel(model.getResult().toString().trim() + "!"));
        } else {
            dialog.add(new JLabel("The winner is " + model.getResult().toString().trim()));
        }

    }

    private void checkResult(int row, int column) {

        if (model.isMarkEmpty(row, column)) {
            model.placeMark(row, column);
            buttons[row][column].setText(model.getMark(row, column).toString());

            if (model.isMarkWin(model.getMark(row, column)) || model.isDraw()) {
                showResult();
            }
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
            move = AIPlayerHard.makeMove(model, maxAIDepth);
        }

        checkResult(move[0], move[1]);
    }

    private void makeInitialComputerMove() {

        int[] move = AIPlayerEasy.makeMove(model);
        checkResult(move[0], move[1]);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (model.anyMovesAvailable()) {
            if (model.isPlayerTurn()) {
                makePlayerMove((JButton) e.getSource());
            }
        }
    }
}
