package com.company;

import java.util.Random;

public class AIPlayerEasy implements AIPlayer {
    Model model;

    public AIPlayerEasy(Model model) {
        this.model = model;
    }

    public int[] makeMove() {
        Random rand = new Random();

        int row = 0, column = 0;
        boolean isCorrect = false;

        while (!isCorrect) {
            row = rand.nextInt(model.getWidth());
            column = rand.nextInt(model.getWidth());

            isCorrect = model.isMarkEmpty(row, column);
        }

        return new int[]{
                row,
                column
        };
    }
}
