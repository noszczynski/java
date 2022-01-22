package com.company;

import java.awt.Color;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;


public class View extends JPanel implements ActionListener {
    private Model model;
    private JButton[][] squares;
    public JLabel resultLabel = new JLabel("Result");

    public View(Model model) {

        this.model = model;

        JPanel squaresPanel = new JPanel(new GridLayout(model.getWidth() + 1, model.getWidth() + 1));

        this.squares = new JButton[model.getWidth()][model.getWidth()];

        // loop through every row and col
        for (int row = 0; row < model.getWidth(); row++) {
            for (int col = 0; col < model.getWidth(); col++) {
                squares[row][col] = new JButton();
                squares[row][col].setBorder(new LineBorder(Color.BLACK));
                squares[row][col].setPreferredSize(new Dimension(124, 124));
                squares[row][col].addActionListener(this);
                squares[row][col].setName("Square" + row + col);

                //testing the creation of buttons
                System.out.println("Created Square [" + row + ":" + col + "]");

                //finish initializing JButton; add to JPanel
                squaresPanel.add(squares[row][col]);
                System.out.println("Square [" + row + ":" + col + "] has been added to the panel.");
            }
        }

        resultLabel.setName("resultLabel");
        resultLabel.setFont(new Font("Courier New", Font.PLAIN, 48));
        squaresPanel.add(resultLabel);

        //add squarePanel to view panel
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

//    public void showInputError() {
//
//        /* Display an error if input is invalid (see examples) */
//        System.out.println("Error: incorrect input");
//    }

    public void showResult(String r) {

        /* Display the final winner */
        resultLabel.setText(model.getResult().toString().toUpperCase().trim());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();

        int row = (int) (btn.getName().charAt(6)) - 48;
        int col = (int) (btn.getName().charAt(7)) - 48;

        System.out.println(row + " " + col);
        if (model.isXTurn()) {
            model.makeMark(row, col);
            btn.setText(model.getMark(row, col).toString());
        } else {
            model.makeMark(row, col);
            btn.setText(model.getMark(row, col).toString());
        }

        if (model.isMarkWin(model.getMark(row, col))) {
            System.out.println(model.getResult().toString());
            showResult(model.getMark(row, col).toString());
        } else if (model.isTie()) {
            System.out.println(model.getResult());
            showResult(model.getMark(row, col).toString().toUpperCase().trim());
        }
    }
}
