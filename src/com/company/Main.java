package com.company;

import javax.swing.JFrame;

// TODO przetestować większe pola gry oraz 4 do końca
// TODO dodać algorytm ruchów komputera

public class Main {
    public static void main(String[] args) {
        try {
            /* Create MVC Objects */
            Model model = new Model();
            View view = new View(model);


            //creating the window
            JFrame frame = new JFrame("Tic Tac Toe by Adam Noszczyński");

            int boardWidth = model.getWidth();
            frame.setSize(boardWidth * 200, boardWidth * 200);
            frame.add(view);

            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
