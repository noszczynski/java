package com.company;

import java.awt.Color;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;


public class View extends JPanel implements ActionListener {
    private final Model model;
    public JLabel resultLabel = new JLabel("Result");

    public View(Model model) {

        this.model = model;
        int modelWidth = this.model.getWidth();

        JPanel squaresPanel = new JPanel(new GridLayout(modelWidth + 1, modelWidth + 1));

        JButton[][] squares = new JButton[modelWidth][modelWidth];

        // loop through every row and col
        for (int row = 0; row < modelWidth; row++) {
            for (int col = 0; col < modelWidth; col++) {

                JButton button = new JButton();
                button.setBorder(new LineBorder(Color.BLACK));
                button.setPreferredSize(new Dimension(124, 124));
                button.addActionListener(this);
                button.setName("Square" + row + col);

                squares[row][col] = button;

                //testing the creation of buttons
//                System.out.println("Created Square [" + row + ":" + col + "]");

                //finish initializing JButton; add to JPanel
                squaresPanel.add(squares[row][col]);
//                System.out.println("Square [" + row + ":" + col + "] has been added to the panel.");
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

    public void showResult(String r) {

        /* Display the final winner */
        resultLabel.setText(model.getResult().toString().toUpperCase().trim());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedBtn = (JButton) e.getSource();

        System.out.println(clickedBtn);

        int row = (int) (clickedBtn.getName().charAt(6)) - 48;
        int col = (int) (clickedBtn.getName().charAt(7)) - 48;

        model.makeMark(row, col);
        clickedBtn.setText(model.getMark(row, col).toString());

        if (model.isMarkWin(model.getMark(row, col))) {
            showResult(model.getMark(row, col).toString());
        } else if (model.isTie()) {
            showResult(model.getMark(row, col).toString().toUpperCase().trim());
        }
    }
}
