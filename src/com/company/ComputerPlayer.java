package com.company;

public class ComputerPlayer extends Player {
    ComputerPlayer(int id, String name, char sign) {
        super(id, name, sign);
    }

    public Move getMove() {
        // TODO random values
        return new Move('A', 1);
    }
}
